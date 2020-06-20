package com.zp.common.security.utils;


import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.regexp.internal.RE;
import com.zp.api.auth.vo.TokenVO;
import com.zp.api.sys.entity.UserEntity;
import com.zp.api.sys.service.SysOpenFeignRoleService;
import com.zp.api.sys.service.SysOpenFeignUserService;
import com.zp.common.config.config.SpringContextUtils;
import com.zp.common.core.exception.RRException;
import com.zp.common.core.util.JwtUtil;
import com.zp.common.core.util.R;
import com.zp.common.core.util.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Component
public class AuthUtils {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	private SysOpenFeignUserService sysOpenFeignUserService;


	private SysOpenFeignRoleService sysOpenFeignRoleService;

	@Resource
	private RedisUtils redisUtils;

	public void initService(){
		if(this.sysOpenFeignRoleService==null){
			this.sysOpenFeignRoleService = SpringContextUtils.getBean(SysOpenFeignRoleService.class);
		}
		if(this.sysOpenFeignUserService==null){
			this.sysOpenFeignUserService = SpringContextUtils.getBean(SysOpenFeignUserService.class);
		}
	}




	public String getUserId() {
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		if(request == null) return null;

		TokenVO token = this.getToken(request);
		if (token==null){
			return null;
		}
		return token.getId();
	}
	
	/**
	 * 获取当前用户 如果没有token中得用户则返回null
	 */
	 public UserEntity getUser() {
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		if(request == null) return null;
		return getUser(request);
		
	} 
	
	/**
	 * 获取当前用户 如果没有token中得用户则返回null
	 */
	 public UserEntity getUser(HttpServletRequest request) {

		 if(request == null) return null;

		 TokenVO token = this.getToken(request);

		 if (token==null){
			 return null;
		 }

		 return getUser(token.getId());
		
	}
	public void clearAll(){

	}


	public TokenVO getToken(HttpServletRequest request) {

		String token = HttpContextUtils.getRequestToken(request);

		if(StringUtils.isBlank(token)){
			return null;
		}
		try {
			JSONObject jsonObject = JwtUtil.analysisToken(token, null);
			TokenVO tokenVO = jsonObject.toJavaObject(TokenVO.class);
			return tokenVO;
		}catch (Exception e){
			e.getStackTrace();
		}
		return null;
	}


	/**
	 * 获取当前用户 如果没有token中得用户则返回null
	 */ 
	public UserEntity getUser(String userId) {


		UserEntity userEntity = redisUtils.get("login_" + userId,UserEntity.class,3600);
		if(userEntity!=null){
			logger.info("从redis中获取用户信息[{}]",userEntity);
			return userEntity;
		}


		this.initService();
		R<UserEntity> r = sysOpenFeignUserService.getLoginUser(userId);
		UserEntity loginUser = r.getData();
		if(loginUser!=null){
			redisUtils.set("login_" + userId,loginUser,3600);
		}
		return loginUser;
		
	}
	


	public Set<String> getUserTokenPerms(String userId) {

		this.initService();

		if("admin".equals(userId)){
			return sysOpenFeignRoleService.getLoginRoleAll().getData();
		}

		UserEntity userEntity = this.getUser(userId);
		List<String> roleIds = userEntity.getRoleIds();
		Set<String> requiresPermissions = new HashSet<>();
		if(roleIds!=null){
			for (String roleId : roleIds) {
				Set<String> perms = redisUtils.get("requiresPermissions_" + roleId, Set.class);
				if(perms!=null&&perms.size()>0){
					requiresPermissions.addAll(perms);
				}else{
					R<Set> r = sysOpenFeignRoleService.getLoginRole(roleId);
					Set<String> data = r.getData();
					if(data!=null&&data.size()>0){
						requiresPermissions.addAll(data);
						redisUtils.set("requiresPermissions_" + roleId,data);
					}
				}
			}
		}

		return requiresPermissions;
	}

}
