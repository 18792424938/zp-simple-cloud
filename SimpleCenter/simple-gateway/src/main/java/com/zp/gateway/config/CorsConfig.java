package com.zp.gateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


@Configuration
public class CorsConfig {

    private static final String MAX_AGE = "18000L";
     
	  @Bean
	    public WebFilter corsFilter() {
		  
	      /*  registry.addMapping("/**")
            .allowedOrigins("*")
            .allowCredentials(true)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")*/
		  
	        return (ServerWebExchange ctx, WebFilterChain chain) -> {
	            ServerHttpRequest request = ctx.getRequest();
	           // System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$" + ctx.getRequest().getURI());
	            ServerHttpResponse response = ctx.getResponse();
	            HttpHeaders headers = response.getHeaders();
	            headers.set("X-Frame-Options", "SAMEORIGIN");
	            if (CorsUtils.isCorsRequest(request)) {
 
	                HttpHeaders requestHeaders = request.getHeaders();
 	                HttpMethod requestMethod = requestHeaders.getAccessControlRequestMethod();
	               
	                //response.setHeader("Access-Control-Allow-Headers", "Content-Type,Access-Token");
	                if(!headers.containsKey(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN))
	                	headers.set(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, requestHeaders.getOrigin());
	                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type,token");
	                if(requestMethod != null){
	                    headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, requestMethod.name());
	                }
	                if(!headers.containsKey(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS))
	                	headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
	                headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
	                headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, MAX_AGE);
	                if (request.getMethod() == HttpMethod.OPTIONS) {
	                    response.setStatusCode(HttpStatus.OK);
	                    return Mono.empty();
	                }
	                
	            }

               
	            return chain.filter(ctx);
	        };
	    }
	  
	  
	 

/**
     * 解决 Only one connection receive subscriber allowed with Content-Type application/x-www-form-urlencoded
     * @return
     */
    /* @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
                return chain.filter(exchange);
            }
        };
    } */
	 
	  /*  @Bean
	    public ServerCodecConfigurer serverCodecConfigurer2() {
	        return new DefaultServerCodecConfigurer();
	    }
	 
	    *//**
	     * 如果使用了注册中心（如：Eureka），进行控制则需要增加如下配置
	     */ 
	   /* @Bean
	    public RouteDefinitionLocator discoveryClientRouteDefinitionLocator2(DiscoveryClient discoveryClient,
	                                                                        DiscoveryLocatorProperties properties) {
	        return new DiscoveryClientRouteDefinitionLocator(discoveryClient, properties);
	    } */
 
}