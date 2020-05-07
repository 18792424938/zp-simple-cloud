package com.zp.module.sys.service.impl;

import com.zp.api.sys.entity.SystemEntity;
import com.zp.common.core.util.PagerUtil;
import com.zp.module.sys.dao.UserDao;
import com.zp.module.sys.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zp.api.sys.entity.UserEntity;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {


    @Override
    public SystemEntity userSystem(String userId) {
        return baseMapper.userSystem(userId);
    }

    @Override
    public UserEntity findByUsername(String username) {
        QueryWrapper<UserEntity> userEntityQueryWrapper = new QueryWrapper<>();
        userEntityQueryWrapper.eq("username",username);
        UserEntity userEntity = this.getOne(userEntityQueryWrapper);
        return userEntity;
    }

    @Override
    public IPage<UserEntity> queryPage(UserEntity User , PagerUtil pagerUtil) {

        QueryWrapper queryWrapper = new QueryWrapper<UserEntity>();
        queryWrapper.orderByDesc("create_date");
        queryWrapper.like(StringUtils.isNotBlank(User.getUsername()),"username",User.getUsername());
        queryWrapper.like(StringUtils.isNotBlank(User.getRealname()),"realname",User.getRealname());

        IPage<UserEntity> ipage = this.page(
                pagerUtil.getPage(UserEntity.class),
                queryWrapper
        );
        return ipage;
    }

}
