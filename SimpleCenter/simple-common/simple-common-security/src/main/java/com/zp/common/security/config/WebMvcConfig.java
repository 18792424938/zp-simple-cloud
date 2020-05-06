package com.zp.common.security.config;


import com.zp.common.security.interceptor.AuthorizationInterceptor;
import com.zp.common.security.resolver.LoginUserHandlerMethodArgumentResolver;
import com.zp.common.security.utils.AnonsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * MVC配置
 *
 * @author zhaipan
 * @email 18792424938@163.com
 * @date 2017-04-20 22:30
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private AuthorizationInterceptor authorizationInterceptor;
    @Resource
    private LoginUserHandlerMethodArgumentResolver loginUserHandlerMethodArgumentResolver;
    @Autowired(required = false)
    private AnonsUtils anonsUtils;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(authorizationInterceptor).excludePathPatterns(anonsUtils!=null?anonsUtils.excludePath():new ArrayList<String>()).addPathPatterns("/**");
    }
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserHandlerMethodArgumentResolver);
    }
}