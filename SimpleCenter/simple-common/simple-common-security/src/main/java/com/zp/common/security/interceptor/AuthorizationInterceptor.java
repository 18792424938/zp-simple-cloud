package com.zp.common.security.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.zp.api.auth.vo.TokenVO;
import com.zp.api.sys.entity.UserEntity;
import com.zp.common.config.config.FeignConfig;
import com.zp.common.core.exception.RRException;
import com.zp.common.core.util.JwtUtil;
import com.zp.common.security.annotation.RequiresPermissions;

import com.zp.common.security.utils.AnonsUtils;
import com.zp.common.security.utils.AuthUtils;
import com.zp.common.security.utils.HttpContextUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 权限(Token)验证
 * @author zhaipan
 * @email 18792424938@163.com
 * @date 2017-03-23 15:38
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
	
	Logger logger = LoggerFactory.getLogger(getClass());


	 
    @Autowired
    private AuthUtils authUtils;


     

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 判断是不是feign调用 如果是直接放行
        String feignToken = HttpContextUtils.getRequestHeaderValue(request, FeignConfig.NO_VALID_HEADER);
        if(StringUtils.isNotBlank(feignToken) && feignToken.equals(FeignConfig.NO_VALID_TOKEN)) {
            logger.info("request is feign . url:[{}]" , request.getRequestURI());
            // 放行 但顺带把feigntoken清空
            //@TODO
            request.setAttribute(FeignConfig.NO_VALID_TOKEN,FeignConfig.NO_VALID_TOKEN);
            return true ;
        }

        String token = HttpContextUtils.getRequestToken(request);

        if(StringUtils.isBlank(token)){
           throw new RRException("请登录", HttpStatus.UNAUTHORIZED.value());
        }
        TokenVO tokenVO = null;
        try {
            JSONObject jsonObject = JwtUtil.analysisToken(token, null);
            tokenVO = jsonObject.toJavaObject(TokenVO.class);
        }catch (Exception e){
            e.getStackTrace();
            throw  new RRException(e.getMessage(), HttpStatus.UNAUTHORIZED.value());
        }

        //放入用户
        UserEntity userEntity = authUtils.getUser();

        request.setAttribute("user",userEntity);

        if("admin".equals(tokenVO.getId())){
            return true;
        }

        if(20 == userEntity.getStatus()){
            throw  new RRException("用户已被禁用", HttpStatus.UNAUTHORIZED.value());
        }
        //刷新时间大于token签发的时间,让重新登录
        if(userEntity.getExpireDate().getTime()>=tokenVO.getIssueTime().getTime()){
            throw  new RRException("token已失效,请重新登录", HttpStatus.UNAUTHORIZED.value());
        }

        //解析token 并获取用户信息
        Set<String> peram = authUtils.getUserTokenPerms(tokenVO.getId());

        //验证当前用户是否有权限
        RequiresPermissions aperm;
        if(handler instanceof HandlerMethod) {
            //  annotation = ((HandlerMethod) handler).getMethodAnnotation(Login.class);
            aperm = ((HandlerMethod) handler).getMethodAnnotation(RequiresPermissions.class);
        }else{
            return true;
        }

        if(aperm==null||StringUtils.isBlank(aperm.value())){
            return true;
        }

        String perm = aperm.value();
        // 判断此处基于角色还是基于具体的权限码
        if(peram.contains(perm.trim())) {
            return true;
        }
        throw new RRException("当前用户无此操作权限", HttpStatus.UNAUTHORIZED.value());
    }
}
