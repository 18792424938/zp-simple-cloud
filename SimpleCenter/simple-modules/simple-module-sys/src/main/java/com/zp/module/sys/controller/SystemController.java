package com.zp.module.sys.controller;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zp.api.sys.entity.*;
import com.zp.common.config.util.PagerUtil;
import com.zp.common.security.annotation.RequiresPermissions;
import com.zp.common.security.utils.AuthUtils;
import com.zp.module.sys.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zp.api.sys.entity.SystemEntity;

import com.zp.common.core.util.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

/**
 * 系统表
 *
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:25
 */
@RestController
@RequestMapping("sys/system")
@Api(value = "系统表API", tags = {"系统表API-通用"})
public class SystemController {
    @Autowired
    private SystemService systemService;

    @Autowired
    private AuthUtils authUtils;

    @Autowired
    private RoleSystemService roleSystemService;


    @Autowired
    private MenuService menuService;






    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:system:list")
    @ApiOperation("系统表列表")
    @ApiResponse(code = 0, message = "查询成功", response = SystemEntity.class)
    public R list(SystemEntity System, PagerUtil pagerUtil) {

        IPage<SystemEntity> pageData = systemService.queryPage(System, pagerUtil);

        return R.ok().setData(pageData);
    }

    /**
     * 列表
     */
    @GetMapping("/listAll")
    @ApiOperation("系统表列表")
    @ApiResponse(code = 0, message = "查询成功", response = SystemEntity.class)
    public R listAll() {
        QueryWrapper<SystemEntity> systemEntityQueryWrapper = new QueryWrapper<>();
        systemEntityQueryWrapper.orderByAsc("create_date");
        List<SystemEntity> systemEntityList = systemService.list(systemEntityQueryWrapper);
        return R.ok().setData(systemEntityList);
    }

    /**
     * 列表
     */
    @GetMapping("/listSystem")
    @ApiOperation("系统表列表")
    @ApiResponse(code = 0, message = "查询成功", response = SystemEntity.class)
    public R listSystem() {
        String userid = authUtils.getUserId();
        List<SystemEntity> systemEntityList = systemService.selectByUserId(userid);
        return R.ok().setData(systemEntityList);
    }




    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @ApiOperation("根据ID获取系统表信息")
    @ApiResponse(code = 0, message = "查询成功", response = SystemEntity.class)
    public R<SystemEntity> info(@PathVariable("id") String id) {
        SystemEntity system = systemService.getById(id);

        return R.ok(SystemEntity.class).setData(system);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("sys:system:save")
    @ApiOperation("保存系统表信息")
    public R<Object> save(@RequestBody SystemEntity system) {
        String userId = authUtils.getUserId();
        Date date = new Date();
        system.setCreateId(userId);
        system.setCreateDate(date);
        system.setUpdateId(userId);
        system.setUpdateDate(date);

        systemService.save(system);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:system:update")
    @ApiOperation("修改系统表信息")
    public R<Object> update(@RequestBody SystemEntity system) {
        String userId = authUtils.getUserId();
        Date date = new Date();
        system.setUpdateId(userId);
        system.setUpdateDate(date);
        systemService.updateById(system);

        return R.ok();
    }

    /**
     * 删除
     */
    @GetMapping("/delete/{id}")
    @RequiresPermissions("sys:system:delete")
    @ApiOperation("删除系统表信息")
    public R<Object> delete(@PathVariable("id") String id) {

        QueryWrapper<MenuEntity> menuEntityQueryWrapper = new QueryWrapper<>();
        menuEntityQueryWrapper.eq("system_id",id);
        int count1 = menuService.count(menuEntityQueryWrapper);

        if(count1>0){
            return R.error("请先删除系统下的菜单");
        }


        systemService.removeMyById(id);

        return R.ok();
    }

}
