package com.zp.module.auth.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.zp.api.auth.vo.LoginVO;
import com.zp.api.auth.vo.TokenVO;
import com.zp.api.sys.entity.UserEntity;
import com.zp.common.core.util.JwtUtil;
import com.zp.common.core.util.R;
import com.zp.common.core.util.RedisUtils;
import com.zp.common.security.utils.AuthUtils;
import com.zp.module.auth.service.LoginService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private AuthUtils authUtils;

    @Autowired
    private RedisUtils redisUtils;


    @Autowired
    private DefaultKaptcha defaultKaptcha;




    @PostMapping("/login")
    public R login(@RequestBody LoginVO loginVO){


        if(StringUtils.isBlank(loginVO.getUsername())){
            return R.error("用户名不能为空");
        }


        if(StringUtils.isBlank(loginVO.getPassword())){
            return R.error("密码不能为空");
        }

        if (StringUtils.isBlank(loginVO.getCaptcha())) {
            return R.error("请输入验证码");
        }

        String captcha = redisUtils.get("captcha_"+loginVO.getCode());
        if(captcha!=null){
            //清除验证码
            redisUtils.del("captcha_"+loginVO.getCode());
            if(!captcha.toLowerCase().equals(loginVO.getCaptcha().toLowerCase())){
                return R.error("验证码不正确");
            }
        }else {
            return R.error("验证码已过期!");
        }

        String token = "";
        try {
            UserEntity userEntity = loginService.login(loginVO);

            TokenVO tokenVO = new TokenVO(userEntity.getId(),userEntity.getUsername(),null);

            JSONObject jsonObject = (JSONObject)JSONObject.toJSON(tokenVO);
            // 生成token
            token = JwtUtil.createToken(jsonObject, null);

        }catch (Exception e){
            return R.error(e.getMessage());
        }

        //加密jwt
        return R.ok().setData(token);
    }

    /**
     * 获取验证码
     * @return
     */
    @GetMapping("/captcha.jpg")
    public R captcha(){

        String kaptchaText = defaultKaptcha.createText();

        BufferedImage image = defaultKaptcha.createImage(kaptchaText);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", stream);
        }catch (Exception e){
            e.getStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        String encode = "data:image/png;base64,"+encoder.encode(stream.toByteArray());

        String code = UUID.randomUUID().toString();
        redisUtils.set("captcha_"+code,kaptchaText,120);

        Map<String,String> data = new HashMap<>();

        data.put("code",code);
        data.put("image",encode);

        return R.ok().setData(data);
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
