package com.zp.module.sys.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.regexp.internal.RE;
import com.zp.api.sys.entity.UploadFileEntity;
import com.zp.api.sys.entity.UserRoleEntity;
import com.zp.common.core.util.PagerUtil;
import com.zp.common.security.annotation.RequiresPermissions;
import com.zp.common.security.utils.AuthUtils;
import com.zp.module.sys.service.UserRoleService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zp.api.sys.entity.UserEntity;
import com.zp.module.sys.service.UserService;

import com.zp.common.core.util.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户表
 *
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:25
 */
@RestController
@RequestMapping("sys/user")
@Api(value = "用户表API", tags = {"用户表API-通用"})
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private AuthUtils authUtils;


    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation("用户表列表")
    @ApiResponse(code = 0, message = "查询成功", response = UserEntity.class)
    public R list(UserEntity userEntity,PagerUtil pagerUtil) {

        IPage<UserEntity> pageData = userService.queryPage(userEntity, pagerUtil);

        return R.ok().setData(pageData);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:user:info")
    @ApiOperation("根据ID获取用户表信息")
    @ApiResponse(code = 0, message = "查询成功", response = UserEntity.class)
    public R<UserEntity> info(@PathVariable("id") String id) {
        UserEntity user = userService.getById(id);

        return R.ok(UserEntity.class).setData(user);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("sys:user:save")
    @ApiOperation("保存用户表信息")
    public R<Object> save(@RequestBody UserEntity user) {

        String userId = authUtils.getUserId();
        Date date = new Date();
        user.setCreateId(userId);
        user.setCreateDate(date);
        user.setUpdateId(userId);
        user.setUpdateDate(date);

        UserEntity userEntity = userService.findByUsername(user.getUsername());

        if (userEntity != null) {
            return R.error("用户名已存在");
        }

        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword("11111111");
        }
        //生成盐
        user.setSalt(RandomStringUtils.randomAscii(12));
        byte[] bytesPassword = (user.getSalt() + "/" + user.getPassword()).getBytes();
        //生成密码
        user.setPassword(DigestUtils.md5DigestAsHex(bytesPassword));

        userService.save(user);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:user:update")
    @ApiOperation("修改用户表信息")
    public R<Object> update(@RequestBody UserEntity user) {

        UserEntity userEntity = userService.getById(user.getId());

        if (userEntity == null) {
            return R.error("用户名不存在");
        }

        String userId = authUtils.getUserId();
        Date date = new Date();


        userEntity.setUpdateId(userId);
        userEntity.setUpdateDate(date);

        userEntity.setRealname(user.getRealname());
        userEntity.setStatus(user.getStatus());

        userService.updateById(userEntity);

        return R.ok();
    }


    /**
     * 启用/禁用
     */
    @PostMapping("/forbidden/{id}")
    @RequiresPermissions("sys:user:forbidden")
    @ApiOperation("修改用户表信息")
    public R<Object> forbidden(@PathVariable("id") String id) {

        UserEntity userEntity = userService.getById(id);

        if (10 == userEntity.getStatus()) {
            userEntity.setStatus(20);
        } else {
            userEntity.setStatus(10);
        }
        userService.updateById(userEntity);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    @ApiOperation("删除用户表信息")
    public R<Object> delete(@RequestBody String[] ids) {
        userService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


    /**
     * 返回用户登录的信息
     */
    @GetMapping("/getLoginUser/{id}")
    @ApiOperation("根据ID获取登录用户信息")
    @ApiResponse(code = 0, message = "查询成功", response = UserEntity.class)
    public R<UserEntity> getLoginUser(@PathVariable("id") String id) {

        QueryWrapper<UserEntity> userEntityQueryWrapper = new QueryWrapper<>();
        userEntityQueryWrapper.eq("id", id);
        userEntityQueryWrapper.select(new String[]{"id", "username", "realname", "status"});

        // 查询用户信息
        UserEntity user = userService.getOne(userEntityQueryWrapper);

        //查询用户角色列表
        QueryWrapper<UserRoleEntity> userRoleEntityQueryWrapper = new QueryWrapper<>();
        userRoleEntityQueryWrapper.eq("id", id);

        List<UserRoleEntity> userRoleEntityList = userRoleService.list(userRoleEntityQueryWrapper);
        List<String> roleids = userRoleEntityList.stream().map(p -> p.getRoleId()).collect(Collectors.toList());
        user.setRoleIds(roleids);

        return R.ok(UserEntity.class).setData(user);
    }


}
