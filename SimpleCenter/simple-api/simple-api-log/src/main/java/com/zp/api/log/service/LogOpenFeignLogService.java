package com.zp.api.log.service;


import com.zp.api.log.constants.LogConstants;
import com.zp.api.log.entity.LoginLogEntity;
import com.zp.api.log.entity.UserLogEntity;
import com.zp.api.log.service.failback.LogOpenFeignLogFailbackFactory;
import com.zp.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(contextId="logOpenFeignLogService",value = LogConstants.SERVICE , fallbackFactory = LogOpenFeignLogFailbackFactory.class )
public interface LogOpenFeignLogService {

    @PostMapping(value="/log/loginLog/save")
    public R saveLoginLog(@RequestBody LoginLogEntity logEntity);

    @PostMapping(value="/log/userLog/save")
    public R saveUserLog(@RequestBody UserLogEntity userLogEntity);


}
