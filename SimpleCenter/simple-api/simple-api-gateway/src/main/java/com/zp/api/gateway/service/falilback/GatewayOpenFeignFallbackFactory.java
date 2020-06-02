package com.zp.api.gateway.service.falilback;


import com.zp.api.gateway.service.GatewayOpenFeignService;
import com.zp.api.gateway.service.impl.GatewayOpenFeignServiceImpl;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;


@Component
public class GatewayOpenFeignFallbackFactory implements FallbackFactory<GatewayOpenFeignService> {


    @Override
    public GatewayOpenFeignService create(Throwable throwable) {
        GatewayOpenFeignServiceImpl si = new GatewayOpenFeignServiceImpl(throwable);
        return si;
    }
}
