package com.zp.api.sys.service;


import com.zp.api.sys.constants.SysConstants;
import com.zp.api.sys.entity.MenuEntity;
import com.zp.api.sys.service.fallback.SysOpenFeignMenuFallbackFactory;
import com.zp.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(contextId="sysOpenFeignMenuService",value = SysConstants.SERVICE , fallbackFactory = SysOpenFeignMenuFallbackFactory.class )
public interface SysOpenFeignMenuService {

    @GetMapping(value="/sys/menu/nav")
    public R<List> getLoginMenu(String systemId);
}
