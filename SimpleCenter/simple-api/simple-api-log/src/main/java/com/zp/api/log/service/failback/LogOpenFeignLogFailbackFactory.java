package com.zp.api.log.service.failback;


import com.zp.api.log.service.LogOpenFeignLogService;

import com.zp.api.log.service.impl.LogOpenFeignLogServiceImpl;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;


@Component
public class LogOpenFeignLogFailbackFactory implements FallbackFactory<LogOpenFeignLogService> {


    @Override
    public LogOpenFeignLogService create(Throwable throwable) {
        LogOpenFeignLogServiceImpl si = new LogOpenFeignLogServiceImpl(throwable);
        return si;
    }
}
