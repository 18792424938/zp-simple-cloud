package com.zp.common.security.utils;


import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.regexp.internal.RE;
import com.zp.api.auth.vo.TokenVO;
import com.zp.api.sys.entity.UserEntity;
import com.zp.api.sys.service.SysOpenFeignRoleService;
import com.zp.api.sys.service.SysOpenFeignUserService;
import com.zp.common.core.exception.RRException;
import com.zp.common.core.util.JwtUtil;
import com.zp.common.core.util.R;
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

	@Resource
	private SysOpenFeignUserService sysOpenFeignUserService;

	@Resource
	private SysOpenFeignRoleService sysOpenFeignRoleService;





	/*@Autowired
	private RedisUtils redisUtils;*/


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
		//查询redis
		//通过openFrie
		R<UserEntity> r = sysOpenFeignUserService.getLoginUser(userId);
		return r.getData();
		
	}
	


	public Set<String> getUserTokenPerms(String userId) {

		Set<String> set = new HashSet<>();

		UserEntity userEntity = this.getUser(userId);

		List roleIds = userEntity.getRoleIds();
		if(roleIds!=null&&roleIds.size()>0){
			R<Map> r = sysOpenFeignRoleService.getLoginRole(roleIds);
			Set<String> tempSet = (Set<String>)r.getData();
			set.addAll(tempSet);
		}
		// 根据redis判断
		/*String key = getClass().getSimpleName() + ".getUserTokenPerms." + token;
		if(redisUtils.containKey(key)) {
			return redisUtils.get(key, Set.class);
		}

		String userId = getUserId(token);


		Set<String> perms = getUserPerms(userId);

		redisUtils.set(key, perms , 10*60);*/

		return set;
	}

}
