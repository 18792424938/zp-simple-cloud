package com.zp.module.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zp.api.auth.vo.LoginVO;
import com.zp.api.sys.entity.UserEntity;


/**
 * 登录
 *
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:25
 */
public interface LoginService extends IService<UserEntity> {

    /**
     * 登录 用户信息登录 返回登录后的用户信息
     * @param loginVO
     * @return
     */
    public UserEntity login(LoginVO loginVO)  throws Exception ;


}

