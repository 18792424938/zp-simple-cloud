package com.zp.module.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zp.module.sys.dao.RoleSystemDao;
import com.zp.module.sys.service.RoleSystemService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.zp.api.sys.entity.RoleSystemEntity;



@Service("roleSystemService")
public class RoleSystemServiceImpl extends ServiceImpl<RoleSystemDao, RoleSystemEntity> implements RoleSystemService {


    public IPage<RoleSystemEntity> queryPage(RoleSystemEntity RoleSystem , IPage<RoleSystemEntity> page ) {
        
        IPage<RoleSystemEntity> ipage = this.page(
                page,
                new QueryWrapper<RoleSystemEntity>()
        );

        return ipage;
    }

}
