package com.zp.api.sys.service.impl;


import com.zp.api.sys.constants.SysConstants;
import com.zp.api.sys.entity.SystemEntity;
import com.zp.api.sys.service.SysOpenFeignSystemService;
import com.zp.common.core.util.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SysOpenFeignSystemServiceImpl implements SysOpenFeignSystemService {

    Logger logger = LoggerFactory.getLogger(getClass());

    private Throwable cause;



    public SysOpenFeignSystemServiceImpl(Throwable cause) {
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
    public R<SystemEntity> getLoginSystem(String id) {
        logger.error(SysConstants.SERVICE, SysOpenFeignSystemServiceImpl.class,"getLoginSystem请求失败{}" , this.cause);
        return R.error(SystemEntity.class);
    }
}
