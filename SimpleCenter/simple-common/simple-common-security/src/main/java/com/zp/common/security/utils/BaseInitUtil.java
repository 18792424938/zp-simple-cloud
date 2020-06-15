package com.zp.common.security.utils;

import com.zp.common.config.config.SpringContextUtils;
import com.zp.common.core.entity.BaseEntity;

import java.util.Date;

public class BaseInitUtil {


    public static void createInit(BaseEntity baseEntity){
        AuthUtils authUtils = SpringContextUtils.getBean(AuthUtils.class);
        String userId = authUtils.getUserId();
        Date date = new Date();
        baseEntity.setCreateDate(date);
        baseEntity.setUpdateDate(date);
        baseEntity.setCreateId(userId);
        baseEntity.setUpdateId(userId);

    }

    public static void updateInit(BaseEntity baseEntity){
        AuthUtils authUtils = SpringContextUtils.getBean(AuthUtils.class);
        String userId = authUtils.getUserId();
        Date date = new Date();
        baseEntity.setCreateId(userId);
        baseEntity.setUpdateId(userId);
    }

}
