package com.zp.module.sys.service;
 
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;


import com.zp.api.sys.entity.SystemEntity;
import com.zp.api.sys.entity.UserEntity;
import com.zp.common.config.util.PagerUtil;

import java.util.List;


/**
 * 用户表
 *
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:25
 */
public interface UserService extends IService<UserEntity> {

    IPage<UserEntity> queryPage(UserEntity User, PagerUtil pagerUtil);
    UserEntity findByUsername(String username);
    SystemEntity userSystem(String userId);
    List<SystemEntity> userSystemList(String userId);
    List<String> userSystemIds(String userId);
    void saveUser(UserEntity userEntity);
    void updateUser(UserEntity userEntity);

}

