package com.zp.module.log.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zp.api.log.entity.LoginLogEntity;
import com.zp.common.config.util.PagerUtil;


/**
 * 字典表
 *
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:26
 */
public interface LoginLogService extends IService<LoginLogEntity> {

    IPage<LoginLogEntity> queryPage(LoginLogEntity Log, PagerUtil pagerUtil);
    boolean saveInfo(LoginLogEntity entity);
}

