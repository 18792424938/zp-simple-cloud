package com.zp.api.sys.service;


import com.zp.api.sys.constants.SysConstants;
import com.zp.api.sys.entity.SystemEntity;
import com.zp.api.sys.service.falilback.SysOpenFeignSystemFallbackFactory;
import com.zp.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(contextId="sysOpenFeignSystemService",value = SysConstants.SERVICE , fallbackFactory = SysOpenFeignSystemFallbackFactory.class )
public interface SysOpenFeignSystemService {

    @GetMapping(value="/sys/user/getLoginSystem/{id}")
    public R<SystemEntity> getLoginSystem(@PathVariable("id") String id);
}
