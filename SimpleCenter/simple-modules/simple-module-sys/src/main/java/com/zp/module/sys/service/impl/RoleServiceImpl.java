package com.zp.module.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zp.module.sys.dao.RoleDao;
import com.zp.module.sys.service.RoleService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;


import com.zp.api.sys.entity.RoleEntity;


@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleDao, RoleEntity> implements RoleService {


    public IPage<RoleEntity> queryPage(RoleEntity Role , IPage<RoleEntity> page ) {
        
        IPage<RoleEntity> ipage = this.page(
                page,
                new QueryWrapper<RoleEntity>()
        );

        return ipage;
    }

}
