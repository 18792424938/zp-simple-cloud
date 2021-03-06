package com.zp.module.log.config;


import com.zp.module.log.util.AddressUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AddressConfig {

    // IP地址查询
    @Value("${syslog.baseUrl:23}")
    private String IP_URL;




    @Bean
    public void  getInstance(){
        AddressUtil.IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";
    }

}
