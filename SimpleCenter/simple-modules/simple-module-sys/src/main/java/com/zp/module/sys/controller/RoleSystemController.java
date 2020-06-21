package com.zp.module.sys.controller;

import java.util.Arrays;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zp.api.sys.entity.RoleSystemEntity;
import com.zp.common.log.annotation.SysLog;
import com.zp.common.log.annotation.SysModule;
import com.zp.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zp.api.sys.entity.RoleSystemEntity;
import com.zp.module.sys.service.RoleSystemService;

import com.zp.common.core.util.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation; 
import io.swagger.annotations.ApiResponse; 

/**
 * 角色系统表
 *
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:25
 */
@RestController
@RequestMapping("sys/rolesystem")
@Api(value = "角色系统表API", tags = { "角色系统表API-通用" })
public class RoleSystemController {
    @Autowired
    private RoleSystemService roleSystemService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:rolesystem:list")
        @ApiOperation("角色系统表列表")
    @ApiResponse(code=0,message="查询成功",response=RoleSystemEntity.class)
	    public R list(RoleSystemEntity RoleSystem , IPage<RoleSystemEntity> page){
       
        IPage<RoleSystemEntity> pageData = roleSystemService.queryPage(RoleSystem , page);

        return R.ok().setData(pageData);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:rolesystem:info")
        @ApiOperation("根据ID获取角色系统表信息")
    @ApiResponse(code=0,message="查询成功",response=RoleSystemEntity.class)
	    public R<RoleSystemEntity> info(@PathVariable("id") String id){
			RoleSystemEntity roleSystem = roleSystemService.getById(id);

        return R.ok(RoleSystemEntity.class).setData( roleSystem);
    }

    /**
     * 保存
     */
    @SysLog(value = "保存角色和系统关系",system= SysModule.sys)
    @PostMapping("/save")
    @RequiresPermissions("sys:rolesystem:save")
        @ApiOperation("保存角色系统表信息") 
	    public R<Object> save(@RequestBody RoleSystemEntity roleSystem){
			roleSystemService.save(roleSystem);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog(value = "修改角色和菜单关系",system= SysModule.sys)
    @PostMapping("/update")
    @RequiresPermissions("sys:rolesystem:update")
        @ApiOperation("修改角色系统表信息") 
	    public R<Object> update(@RequestBody RoleSystemEntity roleSystem){
			roleSystemService.updateById(roleSystem);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog(value = "删除角色和菜单关系",system= SysModule.sys)
    @PostMapping("/delete")
    @RequiresPermissions("sys:rolesystem:delete")
        @ApiOperation("删除角色系统表信息") 
	    public R<Object> delete(@RequestBody String[] ids){
			roleSystemService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
