package com.zp.api.gateway.service;



import com.zp.api.gateway.service.constants.SysConstants;
import com.zp.api.gateway.service.falilback.GatewayOpenFeignFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(contextId="gatewayOpenFeignService",value = SysConstants.SERVICE , fallbackFactory = GatewayOpenFeignFallbackFactory.class )
public interface GatewayOpenFeignService {

    @GetMapping(value="/gateway/server/init")
    public void init();

}
