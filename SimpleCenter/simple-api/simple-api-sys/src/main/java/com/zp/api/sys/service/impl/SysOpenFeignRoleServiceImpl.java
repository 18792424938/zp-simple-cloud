package com.zp.api.sys.service.impl;


import com.zp.api.sys.constants.SysConstants;
import com.zp.api.sys.entity.RoleEntity;
import com.zp.api.sys.service.SysOpenFeignRoleService;
import com.zp.common.core.util.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SysOpenFeignRoleServiceImpl implements SysOpenFeignRoleService {

    Logger logger = LoggerFactory.getLogger(getClass());

    private Throwable cause;



    public SysOpenFeignRoleServiceImpl(Throwable cause) {
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
    public R<Set> getLoginRoleAll() {
        logger.error(SysConstants.SERVICE, SysOpenFeignRoleServiceImpl.class,"getLoginRole请求失败{}" , this.cause);
        return R.error(Set.class);
    }

    @Override
    public R<Set> getLoginRole(String id) {
        logger.error(SysConstants.SERVICE, SysOpenFeignRoleServiceImpl.class,"getLoginRole请求失败{}" , this.cause);
        return R.error(Set.class);
    }
}
