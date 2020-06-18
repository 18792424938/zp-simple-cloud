package com.zp.api.sys.service;


import com.zp.api.sys.constants.SysConstants;
import com.zp.api.sys.service.falilback.SysOpenFeignRoleFallbackFactory;
import com.zp.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@FeignClient(contextId="sysOpenFeignRoleService",value = SysConstants.SERVICE , fallbackFactory = SysOpenFeignRoleFallbackFactory.class )
public interface SysOpenFeignRoleService {

    @GetMapping(value="/sys/role/getLoginRole/{id}")
    public R<Set> getLoginRole(@PathVariable("id") String id);

    @GetMapping(value="/sys/role/getLoginRoleAll")
    public R<Set> getLoginRoleAll();
}
