package com.zp.module.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zp.api.sys.entity.UserTokenExpireEntity;
import com.zp.common.config.util.PagerUtil;

import java.util.Date;


/**
 * 用户表
 *
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:25
 */
public interface UserTokenExpireService extends IService<UserTokenExpireEntity> {

    IPage<UserTokenExpireEntity> queryPage(UserTokenExpireEntity User, PagerUtil pagerUtil);
    Date getExpireDate(String userId);
    void saveExpire(String userId);

}

