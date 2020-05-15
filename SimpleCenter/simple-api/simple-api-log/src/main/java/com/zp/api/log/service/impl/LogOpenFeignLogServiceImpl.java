package com.zp.api.log.service.impl;


import com.zp.api.log.constants.LogConstants;
import com.zp.api.log.entity.LogEntity;
import com.zp.api.log.entity.UserLogEntity;
import com.zp.api.log.service.LogOpenFeignLogService;
import com.zp.common.core.util.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

import java.util.List;
import java.util.Set;

public class LogOpenFeignLogServiceImpl implements LogOpenFeignLogService {

    Logger logger = LoggerFactory.getLogger(getClass());

    private Throwable cause;



    public LogOpenFeignLogServiceImpl(Throwable cause) {
        super();
        this.cause = cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    public Throwable getCause() {
        return cause;
    }

    @Override
    public R saveLog(LogEntity logEntity) {
        logger.error(LogConstants.SERVICE, LogOpenFeignLogServiceImpl.class,"save{}" , this.cause);
        return R.error(List.class);
    }
    @Override
    public R saveUserLog(UserLogEntity userLogEntity) {
        logger.error(LogConstants.SERVICE, LogOpenFeignLogServiceImpl.class,"save{}" , this.cause);
        return R.error(List.class);
    }
}
