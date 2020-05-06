package com.zp.common.security.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class HttpContextUtils {

	public static HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	public static String getDomain(){
		HttpServletRequest request = getHttpServletRequest();
		StringBuffer url = request.getRequestURL();
		return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
	}

	public static String getOrigin(){
		HttpServletRequest request = getHttpServletRequest();
		return request.getHeader("Origin");
	}
	
	  /**
     * 获取请求的token
     */
    public static String getRequestToken(HttpServletRequest httpRequest){
        //从header中获取token 

        return getRequestHeaderValue(httpRequest , "token");
    }
    
    /**
     * 获取请求的token
     */
    public static String getRequestHeaderValue(HttpServletRequest request , String name){
        //从header中获取token
        String token = request.getHeader(name);
        //从Header获取失败就从cookie中获取
        if(StringUtils.isBlank(token)){
            Cookie[] cookies = request.getCookies();
            if(cookies != null && cookies.length > 0){
                for (Cookie cookie : cookies){
                    if ("token".equals(cookie.getName())){
                        token = cookie.getValue();
                        break;
                    }
                }
            }
        }
        return token;
    }
}
