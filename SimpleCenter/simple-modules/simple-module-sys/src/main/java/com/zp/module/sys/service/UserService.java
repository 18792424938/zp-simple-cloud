package com.zp.module.sys.service;
 
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;


import com.zp.api.sys.entity.UserEntity;
 

/**
 * 用户表
 *
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:25
 */
public interface UserService extends IService<UserEntity> {

    IPage<UserEntity> queryPage(UserEntity User, IPage<UserEntity> page);
    UserEntity findByUsername(String username);

}

