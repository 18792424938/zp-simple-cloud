package com.zp.module.sys.service;
 
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;


import com.zp.api.sys.entity.UserRoleEntity;
 

/**
 * 用户角色表
 *
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:24
 */
public interface UserRoleService extends IService<UserRoleEntity> {

    IPage<UserRoleEntity> queryPage(UserRoleEntity UserRole, IPage<UserRoleEntity> page);
}

