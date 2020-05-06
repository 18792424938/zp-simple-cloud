package com.zp.api.sys.service;


import com.zp.api.sys.constants.SysConstants;
import com.zp.api.sys.entity.UserEntity;
import com.zp.api.sys.service.fallback.SysOpenFeignUserFallbackFactory;
import com.zp.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(contextId="sysOpenFeignUserService",value = SysConstants.SERVICE , fallbackFactory = SysOpenFeignUserFallbackFactory.class )
public interface SysOpenFeignUserService {

    @GetMapping(value="/sys/user/getLoginUser/{id}")
    public R<UserEntity> getLoginUser(@PathVariable("id") String id);
}
