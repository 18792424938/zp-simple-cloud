package com.zp.module.sys.service.impl;

import com.zp.api.sys.entity.SystemEntity;
import com.zp.api.sys.entity.UserRoleEntity;
import com.zp.common.config.util.PagerUtil;
import com.zp.module.sys.dao.UserDao;
import com.zp.module.sys.service.UserRoleService;
import com.zp.module.sys.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zp.api.sys.entity.UserEntity;

import java.util.LinkedList;
import java.util.List;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Autowired
    private UserRoleService userRoleService;


    @Override
    public void saveUser(UserEntity userEntity) {
        this.save(userEntity);
        List<String> userEntityRoleIds = userEntity.getRoleIds();

        List<UserRoleEntity> userRoleEntityList = new LinkedList<>();
        for (String userEntityRoleId : userEntityRoleIds) {
            userRoleEntityList.add(new UserRoleEntity(null,userEntity.getId(),userEntityRoleId));
        }
        if(userRoleEntityList.size()>0){
            userRoleService.saveBatch(userRoleEntityList);
        }

    }

    @Override
    public void updateUser(UserEntity userEntity) {
        this.updateById(userEntity);
        List<String> userEntityRoleIds = userEntity.getRoleIds();
        userRoleService.remove(new QueryWrapper<UserRoleEntity>().eq("user_id",userEntity.getId()));
        List<UserRoleEntity> userRoleEntityList = new LinkedList<>();
        for (String userEntityRoleId : userEntityRoleIds) {
            userRoleEntityList.add(new UserRoleEntity(null,userEntity.getId(),userEntityRoleId));
        }
        if(userRoleEntityList.size()>0){
            userRoleService.saveBatch(userRoleEntityList);
        }
    }

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
        queryWrapper.ne("id","admin");
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
