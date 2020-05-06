package com.zp.common.security.resolver;


import com.zp.api.sys.entity.UserEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 有@LoginUser注解的方法参数，注入当前登录用户
 * @author zhaipan
 * @email  @email 18792424938@163.com
 * @date 2017-03-23 22:02
 */
@Component
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
     
    /*@Autowired
    private AuthUtils authUtils;*/

    private Logger logger = LoggerFactory.getLogger(getClass());
 
     

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        logger.info(parameter.toString());
        return true;//parameter.getParameterType().isAssignableFrom(SysUserEntity.class)  && parameter.hasParameterAnnotation(LoginUser.class) ;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory){
       
    	
    	
    	UserEntity user = new UserEntity();
    	
    	// 判断request里是否已经由了user
    	/*Object user_ = request.getAttribute("user", RequestAttributes.SCOPE_REQUEST);
    	if(user_ != null && user_ instanceof SysUserEntity) {
    		user = (SysUserEntity) user_ ;
    	}
    	if(user == null) {
    		//获取用户ID
            	
            	// 再次判断请求的request里是否包含token 尝试通过token获取user
    		 //获取用户凭证
            String token = HttpContextUtils.getRequestToken(request);
    		*//*String token = request.getHeader(jwtUtils.getHeader());
    		if(StringUtils.isBlank(token)){
    			token = request.getParameter(jwtUtils.getHeader());
    		}*//*

    		if(StringUtils.isNotBlank(token)) {

    			// user 存在说明还在有效期并且用户存在
    			 //获取用户凭证 
    			//user = authUtils.getUser(token);

    		}
            	 
            
    	}*/
    	 
        return null;
     
    }
}
