package com.zp.api.sys.service.falilback;


import com.zp.api.sys.service.SysOpenFeignSystemService;
import com.zp.api.sys.service.impl.SysOpenFeignSystemServiceImpl;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;


@Component
public class SysOpenFeignSystemFallbackFactory implements FallbackFactory<SysOpenFeignSystemService> {


    @Override
    public SysOpenFeignSystemService create(Throwable throwable) {
        SysOpenFeignSystemServiceImpl si = new SysOpenFeignSystemServiceImpl(throwable);
        return si;
    }
}
