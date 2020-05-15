package com.zp.api.sys.service.falilback;


import com.zp.api.sys.service.SysOpenFeignRoleService;
import com.zp.api.sys.service.impl.SysOpenFeignRoleServiceImpl;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;


@Component
public class SysOpenFeignRoleFallbackFactory implements FallbackFactory<SysOpenFeignRoleService> {


    @Override
    public SysOpenFeignRoleService create(Throwable throwable) {
        SysOpenFeignRoleServiceImpl si = new SysOpenFeignRoleServiceImpl(throwable);
        return si;
    }
}
