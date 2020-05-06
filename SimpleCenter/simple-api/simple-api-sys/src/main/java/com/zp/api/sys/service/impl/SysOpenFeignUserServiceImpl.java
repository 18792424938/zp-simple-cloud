package com.zp.api.sys.service.impl;


import com.zp.api.sys.constants.SysConstants;
import com.zp.api.sys.entity.UserEntity;
import com.zp.api.sys.service.SysOpenFeignUserService;
import com.zp.common.core.util.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SysOpenFeignUserServiceImpl implements SysOpenFeignUserService {

    Logger logger = LoggerFactory.getLogger(getClass());

    private Throwable cause;



    public  SysOpenFeignUserServiceImpl(Throwable cause) {
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
    public R<UserEntity> getLoginUser(String id) {
        logger.error(SysConstants.SERVICE,SysOpenFeignUserServiceImpl.class,"getLoginUser请求失败{}" , this.cause);
        return R.error(UserEntity.class);
    }
}
