package com.zp.module.auth.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zp.api.auth.vo.LoginVO;
import com.zp.api.auth.vo.TokenVO;
import com.zp.api.sys.entity.UserEntity;
import com.zp.common.core.util.JwtUtil;
import com.zp.common.core.util.R;
import com.zp.common.security.utils.AuthUtils;
import com.zp.module.auth.service.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private AuthUtils authUtils;






    @PostMapping("/login")
    public R login(@RequestBody LoginVO loginVO){

        if(StringUtils.isBlank(loginVO.getUsername())){
            return R.error("用户名不能为空");
        }
        if(StringUtils.isBlank(loginVO.getPassword())){
            return R.error("密码不能为空");
        }
        if(!"".equals(loginVO.getCaptcha())){
            return R.error("验证码不正确");
        }

        String token = "";
        try {
            UserEntity userEntity = loginService.login(loginVO);

            TokenVO tokenVO = new TokenVO(userEntity.getId(),userEntity.getUsername());

            JSONObject jsonObject = (JSONObject)JSONObject.toJSON(tokenVO);
            // 生成token
            token = JwtUtil.createToken(jsonObject, null);

        }catch (Exception e){
            return R.error(e.getMessage());
        }
        //加密jwt
        return R.ok().setData(token);
    }

    @GetMapping("/user")
    public R user(){
        UserEntity user = authUtils.getUser();
        user.setRoleIds(null);
        return R.ok().setData(user);
    }

    @GetMapping("/nav")
    public R nav(){
        UserEntity user = authUtils.getUser();
        user.setRoleIds(null);
        //加密jwt
        return R.ok().setData(user);
    }




}
