package com.zp.api.sys.service.falilback;


import com.zp.api.sys.service.SysOpenFeignUserService;
import com.zp.api.sys.service.impl.SysOpenFeignUserServiceImpl;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;


@Component
public class SysOpenFeignUserFallbackFactory implements FallbackFactory<SysOpenFeignUserService> {


    @Override
    public SysOpenFeignUserService create(Throwable throwable) {
        SysOpenFeignUserServiceImpl si = new SysOpenFeignUserServiceImpl(throwable);
        return si;
    }
}
