package com.zp.api.sys.service.impl;


import com.zp.api.sys.constants.SysConstants;
import com.zp.api.sys.entity.MenuEntity;
import com.zp.api.sys.service.SysOpenFeignMenuService;
import com.zp.common.core.util.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SysOpenFeignMenuServiceImpl implements SysOpenFeignMenuService {

    Logger logger = LoggerFactory.getLogger(getClass());

    private Throwable cause;



    public SysOpenFeignMenuServiceImpl(Throwable cause) {
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
    public R<List> getLoginMenu(String id) {
        logger.error(SysConstants.SERVICE, SysOpenFeignMenuServiceImpl.class,"getLoginMenu请求失败{}" , this.cause);
        return R.error(List.class);
    }
}
