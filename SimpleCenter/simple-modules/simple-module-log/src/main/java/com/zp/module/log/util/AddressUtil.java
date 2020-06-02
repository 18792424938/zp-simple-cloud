package com.zp.module.log.util;

import com.alibaba.fastjson.JSONObject;
import com.zp.common.config.util.IPUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * 获取地址类
 *
 * @author zhaipan
 */
public class AddressUtil {
    private static final Logger log = LoggerFactory.getLogger(AddressUtil.class);

    // IP地址查询
    public static String IP_URL;


    // 未知地址
    public  static String UNKNOWN = "XX XX";

    public static String getRealAddressByIP(String ip) {

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
            String addr = obj.getString("addr");
            if(StringUtils.isNotBlank(addr)){
                return addr;
            }
            return String.format("%s %s", region, city);
        } catch (Exception e) {
            log.error("获取地理位置异常 {}", e);
        }
        return address;
    }
}
