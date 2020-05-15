package com.zp.module.log.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zp.api.log.entity.LogEntity;
import com.zp.common.core.util.PagerUtil;


/**
 * 字典表
 *
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:26
 */
public interface LogService extends IService<LogEntity> {

    IPage<LogEntity> queryPage(LogEntity Log, PagerUtil pagerUtil);
}

