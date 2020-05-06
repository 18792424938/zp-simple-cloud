package com.zp.api.sys.service.fallback;


import com.zp.api.sys.service.SysOpenFeignMenuService;
import com.zp.api.sys.service.impl.SysOpenFeignMenuServiceImpl;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;


@Component
public class SysOpenFeignMenuFallbackFactory implements FallbackFactory<SysOpenFeignMenuService> {


    @Override
    public SysOpenFeignMenuService create(Throwable throwable) {
        SysOpenFeignMenuServiceImpl si = new SysOpenFeignMenuServiceImpl(throwable);
        return si;
    }
}
