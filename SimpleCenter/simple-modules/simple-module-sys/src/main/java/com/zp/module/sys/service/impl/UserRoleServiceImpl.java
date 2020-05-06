package com.zp.module.sys.service.impl;

import com.zp.module.sys.dao.UserRoleDao;
import com.zp.module.sys.service.UserRoleService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zp.api.sys.entity.UserRoleEntity;


@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRoleEntity> implements UserRoleService {


    public IPage<UserRoleEntity> queryPage(UserRoleEntity UserRole , IPage<UserRoleEntity> page ) {
        
        IPage<UserRoleEntity> ipage = this.page(
                page,
                new QueryWrapper<UserRoleEntity>()
        );

        return ipage;
    }

}
