package com.zp.module.log.util;

import com.alibaba.fastjson.JSONObject;
import com.zp.common.config.util.IPUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 获取地址类
 *
 * @author ruoyi
 */
@Component
public class AddressUtils {
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    // IP地址查询
    @Value("${log.url}")
    private String IP_URL;

    // 未知地址
    public  String UNKNOWN = "XX XX";

    public String getRealAddressByIP(String ip) {

        String address = UNKNOWN;
        // 内网不查询
        if (IPUtils.internalIp(ip)) {
            return "内网IP";
        }
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> forEntity = restTemplate.getForEntity(IP_URL + "?ip=" + ip + "&json=true", String.class);

            if (StringUtils.isEmpty(forEntity.getBody())) {
                log.error("获取地理位置异常 {}", ip);
                return address;
            }
            JSONObject obj = JSONObject.parseObject(forEntity.getBody());
            String region = obj.getString("pro");
            String city = obj.getString("city");
            return String.format("%s %s", region, city);
        } catch (Exception e) {
            log.error("获取地理位置异常 {}", e);
        }
        return address;
    }
}
