package com.zp.module.sys.config;

import com.zp.common.security.utils.AnonsUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnonsConfig {


    /**
     * 注入要过滤的url
     * @return
     */
    @Bean
    public AnonsUtils initAnonsUtils(){
        AnonsUtils anonsUtils = new AnonsUtils();
        anonsUtils.add("/base/info");
        return anonsUtils;
    }

}
