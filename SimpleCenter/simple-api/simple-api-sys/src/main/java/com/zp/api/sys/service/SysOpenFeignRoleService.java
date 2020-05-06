package com.zp.api.sys.service;


import com.zp.api.sys.constants.SysConstants;
import com.zp.api.sys.entity.RoleEntity;
import com.zp.api.sys.service.fallback.SysOpenFeignRoleFallbackFactory;
import com.zp.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Set;

@FeignClient(contextId="sysOpenFeignRoleService",value = SysConstants.SERVICE , fallbackFactory = SysOpenFeignRoleFallbackFactory.class )
public interface SysOpenFeignRoleService {

    @PostMapping(value="/sys/role/getLoginRole")
    public R<Map> getLoginRole(@RequestBody List<String> ids);
}
