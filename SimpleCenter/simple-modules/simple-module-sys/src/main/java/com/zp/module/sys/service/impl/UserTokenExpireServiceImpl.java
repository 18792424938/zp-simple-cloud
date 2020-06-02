package com.zp.module.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zp.api.sys.entity.UserTokenExpireEntity;
import com.zp.common.config.util.PagerUtil;
import com.zp.common.core.util.RedisUtils;
import com.zp.module.sys.dao.UserTokenExpireDao;
import com.zp.module.sys.service.UserTokenExpireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service("userTokenExpireService")
public class UserTokenExpireServiceImpl extends ServiceImpl<UserTokenExpireDao, UserTokenExpireEntity> implements UserTokenExpireService {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public IPage<UserTokenExpireEntity> queryPage(UserTokenExpireEntity User, PagerUtil pagerUtil) {

        QueryWrapper queryWrapper = new QueryWrapper<UserTokenExpireEntity>();

        IPage<UserTokenExpireEntity> ipage = this.page(
                pagerUtil.getPage(UserTokenExpireEntity.class),
                queryWrapper
        );
        return ipage;
    }

    @Override
    public void saveExpire(String userId) {
        UserTokenExpireEntity userTokenExpireEntity = new UserTokenExpireEntity();
        userTokenExpireEntity.setExpireDate(new Date());
        userTokenExpireEntity.setUserId(userId);
        this.save(userTokenExpireEntity);
        redisUtils.del("login_"+userId);
    }

    public Date getExpireDate(String userId){
        QueryWrapper queryWrapper = new QueryWrapper<UserTokenExpireEntity>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.orderByDesc("expire_date");
        queryWrapper.last("limit 1");
        UserTokenExpireEntity userTokenExpireEntity = this.getOne(queryWrapper);
        return userTokenExpireEntity==null?new Date(0):userTokenExpireEntity.getExpireDate();
    }
}
