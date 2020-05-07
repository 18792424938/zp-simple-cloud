package com.zp.module.sys.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zp.api.sys.entity.SystemEntity;
import com.zp.common.core.util.PagerUtil;
import com.zp.common.security.annotation.RequiresPermissions;
import com.zp.common.security.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zp.api.sys.entity.SystemEntity;
import com.zp.module.sys.service.SystemService;

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
        systemEntityQueryWrapper.orderByDesc("create_date");
        List<SystemEntity> systemEntityList = systemService.list(systemEntityQueryWrapper);
        return R.ok().setData(systemEntityList);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:system:info")
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
    @PostMapping("/delete")
    @RequiresPermissions("sys:system:delete")
    @ApiOperation("删除系统表信息")
    public R<Object> delete(@RequestBody String[] ids) {
        systemService.removeMyByIds(Arrays.asList(ids));

        return R.ok();
    }

}
