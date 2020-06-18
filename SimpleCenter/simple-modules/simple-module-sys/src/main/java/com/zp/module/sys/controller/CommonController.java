package com.zp.module.sys.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zp.api.sys.entity.OrganizationEntity;
import com.zp.api.sys.entity.UserEntity;
import com.zp.api.sys.entity.UserRoleEntity;
import com.zp.api.sys.enums.SysEnum;
import com.zp.api.sys.vo.PasswordVO;
import com.zp.common.config.util.PagerUtil;
import com.zp.common.core.util.R;
import com.zp.common.core.util.RedisUtils;
import com.zp.common.log.annotation.SysLog;
import com.zp.common.log.annotation.SysModule;
import com.zp.common.security.annotation.RequiresPermissions;
import com.zp.common.security.utils.AuthUtils;
import com.zp.module.sys.service.OrganizationService;
import com.zp.module.sys.service.UserRoleService;
import com.zp.module.sys.service.UserService;
import com.zp.module.sys.service.UserTokenExpireService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户表
 *
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:25
 */
@RestController
@RequestMapping("sys/conmon")
@Api(value = "通用api", tags = {"CommonController-通用"})
public class CommonController {
    @Autowired
    private RedisUtils redisUtils;







    /**
     * 缓存列表
     */
    @SysLog(value = "用户列表",system = SysModule.sys)
    @GetMapping("/redislist")
    @ApiOperation("用户表列表")
    @ApiResponse(code = 0, message = "查询成功", response = UserEntity.class)
    public R redislist(String key) {
        Set<String> keys = new HashSet<>();
        if(StringUtils.isNotBlank(key)){
            keys = redisUtils.keys("*" + key + "*");
        }else{
            keys = redisUtils.keys("*");
        }

        JSONArray jsonArray = new JSONArray();

        for (String s : keys) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("key",s);
            jsonObject.put("data",JSONObject.toJSONString(redisUtils.get(s,Object.class)));
            jsonObject.put("expire",redisUtils.ttl(s));
            jsonArray.add(jsonObject);
        }

        return R.ok().setData(jsonArray);
    }


    /**
     * 删除缓存
     */
    @SysLog(value = "删除缓存",system=SysModule.sys)
    @GetMapping("/delredis")
    @RequiresPermissions("sys:delredis")
    @ApiOperation("修改用户表信息")
    public R<Object> delredis(String key) {
        redisUtils.del(key);
        return R.ok();
    }


    /**
     * 在线用户列表
     */
    @SysLog(value = "在线用户列表",system = SysModule.sys)
    @GetMapping("/onlineuser")
    @ApiOperation("在线用户列表")
    @ApiResponse(code = 0, message = "查询成功", response = UserEntity.class)
    public R onlineuser(String username) {
        Set<String> keys = new HashSet<>();
        if(StringUtils.isNotBlank(username)){
            keys = redisUtils.keys("login_*" + username + "*");
        }else{
            keys = redisUtils.keys("login_*");
        }

        List<UserEntity> list = new LinkedList<>();

        for (String key : keys) {
            UserEntity userEntity = redisUtils.get(key,UserEntity.class);
            list.add(userEntity);
        }

        return R.ok().setData(list);
    }


}
