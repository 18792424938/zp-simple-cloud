package com.zp.module.sys.service.impl;

import com.zp.module.sys.dao.UserDao;
import com.zp.module.sys.service.UserService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zp.api.sys.entity.UserEntity;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {


    @Override
    public UserEntity findByUsername(String username) {
        QueryWrapper<UserEntity> userEntityQueryWrapper = new QueryWrapper<>();
        userEntityQueryWrapper.eq("username",username);
        UserEntity userEntity = this.getOne(userEntityQueryWrapper);
        return userEntity;
    }

    @Override
    public IPage<UserEntity> queryPage(UserEntity User , IPage<UserEntity> page ) {
        
        IPage<UserEntity> ipage = this.page(
                page,
                new QueryWrapper<UserEntity>()
        );

        return ipage;
    }

}
