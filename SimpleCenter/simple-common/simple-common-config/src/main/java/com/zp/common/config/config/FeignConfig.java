package com.zp.common.config.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * feign调用混入免验证得token
 * @author zhaipan
 *
 */
@Configuration
public class FeignConfig implements RequestInterceptor {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	public static final String NO_VALID_TOKEN = "zp-novalid" ;
	
	public static final String NO_VALID_HEADER = "feign-token_" ;
	
	@Override
	public void apply(RequestTemplate template) {
		// TODO Auto-generated method stub
		logger.info("template [{}] add feign token." , template.request().url()  );
		template.header(NO_VALID_HEADER, NO_VALID_TOKEN);
	}


}
 