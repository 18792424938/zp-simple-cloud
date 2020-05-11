package com.zp.module.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zp.api.auth.vo.LoginVO;
import com.zp.api.sys.entity.UserEntity;
import com.zp.module.auth.dao.LoginDao;
import com.zp.module.auth.service.LoginService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


@Service("loginService")
public class LoginServiceImpl extends ServiceImpl<LoginDao, UserEntity> implements LoginService {

    @Override
    public UserEntity login(LoginVO loginVO) throws Exception {
        QueryWrapper<UserEntity> userEntityQueryWrapper = new QueryWrapper<>();
        userEntityQueryWrapper.eq("username",loginVO.getUsername());

        UserEntity userEntity = this.getOne(userEntityQueryWrapper);
        if(userEntity==null){
            throw new Exception("用户不存在");
        }

        if(userEntity.getStatus()==20){
            throw new Exception("用户已被禁用");
        }

        byte[] bytesPassword = (userEntity.getSalt() + "/" + loginVO.getPassword()).getBytes();
        //验证密码
        if (!userEntity.getPassword().equals(DigestUtils.md5DigestAsHex(bytesPassword))){
            throw new Exception("密码错误");
        }

        return userEntity;
    }
}
