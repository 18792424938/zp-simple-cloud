package com.zp.module.auth.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.zp.api.auth.vo.LoginVO;
import com.zp.api.auth.vo.TokenVO;
import com.zp.api.log.entity.LoginLogEntity;
import com.zp.api.log.service.LogOpenFeignLogService;
import com.zp.api.sys.entity.UserEntity;
import com.zp.common.config.util.IPUtils;
import com.zp.common.core.exception.RRException;
import com.zp.common.core.util.JwtUtil;
import com.zp.common.core.util.R;
import com.zp.common.core.util.RedisUtils;
import com.zp.common.security.utils.AuthUtils;
import com.zp.module.auth.event.LoginLogEvent;
import com.zp.module.auth.service.LoginService;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
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


    @Autowired
    private ApplicationContext applicationContext;



    @PostMapping("/login")
    public R login(@RequestBody LoginVO loginVO, HttpServletRequest request){



        if(StringUtils.isBlank(loginVO.getUsername())){
            return R.error("用户名不能为空");
        }


        if(StringUtils.isBlank(loginVO.getPassword())){
            return R.error("密码不能为空");
        }

        if (StringUtils.isBlank(loginVO.getCaptcha())) {
            return R.error("请输入验证码");
        }


        LoginLogEntity loginLogEntity = new LoginLogEntity();

        loginLogEntity.setUsername(loginVO.getUsername());
        loginLogEntity.setCaptcha(loginVO.getCaptcha());
        loginLogEntity.setIp(IPUtils.getIpAddr(request));

        //获取浏览器信息
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        loginLogEntity.setSystem(userAgent.getOperatingSystem().getName());
        loginLogEntity.setBrowser(userAgent.getBrowser().getName());
        loginLogEntity.setBrowserVersion(userAgent.getBrowserVersion().getVersion());

        String token = "";
        try {
            String captcha = redisUtils.get("captcha_"+loginVO.getCode());
            if(captcha!=null){
                //清除验证码
                redisUtils.del("captcha_"+loginVO.getCode());
                if(!captcha.toLowerCase().equals(loginVO.getCaptcha().toLowerCase())){
                    throw new RRException("验证码不正确");
                }
            }else {
                throw new RRException("验证码已过期!");
            }

            UserEntity userEntity = loginService.login(loginVO);

            TokenVO tokenVO = new TokenVO(userEntity.getId(),userEntity.getUsername(),null);

            JSONObject jsonObject = (JSONObject)JSONObject.toJSON(tokenVO);
            // 生成token
            token = JwtUtil.createToken(jsonObject, null);


            loginLogEntity.setStatus(10);
            loginLogEntity.setInfo("登录成功");
            LoginLogEvent loginLogEvent = new LoginLogEvent(loginLogEntity);
            applicationContext.publishEvent(loginLogEvent);
        }catch (Exception e){
            loginLogEntity.setStatus(20);
            loginLogEntity.setInfo(e.getMessage());
            LoginLogEvent loginLogEvent = new LoginLogEvent(loginLogEntity);
            applicationContext.publishEvent(loginLogEvent);
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




}
