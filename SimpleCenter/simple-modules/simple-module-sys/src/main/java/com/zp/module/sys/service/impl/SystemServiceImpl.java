package com.zp.module.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zp.module.sys.dao.SystemDao;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.zp.api.sys.entity.SystemEntity;
import com.zp.module.sys.service.SystemService;


@Service("systemService")
public class SystemServiceImpl extends ServiceImpl<SystemDao, SystemEntity> implements SystemService {

    public IPage<SystemEntity> queryPage(SystemEntity System , IPage<SystemEntity> page ) {
        
        IPage<SystemEntity> ipage = this.page(
                page,
                new QueryWrapper<SystemEntity>()
        );

        return ipage;
    }

}
