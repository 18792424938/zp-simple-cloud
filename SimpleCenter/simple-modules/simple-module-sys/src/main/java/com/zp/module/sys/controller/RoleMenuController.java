package com.zp.module.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zp.api.sys.entity.RoleMenuEntity;
import com.zp.common.core.util.R;
import com.zp.common.security.annotation.RequiresPermissions;
import com.zp.module.sys.service.RoleMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 角色系统表
 *
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:25
 */
@RestController
@RequestMapping("sys/rolemenu")
@Api(value = "角色系统表API", tags = { "角色系统表API-通用" })
public class RoleMenuController {
    @Autowired
    private RoleMenuService roleMenuService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:rolemenu:list")
        @ApiOperation("角色系统表列表")
    @ApiResponse(code=0,message="查询成功",response=RoleMenuEntity.class)
	    public R list(RoleMenuEntity RoleMenu , IPage<RoleMenuEntity> page){
       
        IPage<RoleMenuEntity> pageData = roleMenuService.queryPage(RoleMenu , page);

        return R.ok().setData(pageData);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:rolemenu:info")
        @ApiOperation("根据ID获取角色系统表信息")
    @ApiResponse(code=0,message="查询成功",response=RoleMenuEntity.class)
	    public R<RoleMenuEntity> info(@PathVariable("id") String id){
			RoleMenuEntity roleMenu = roleMenuService.getById(id);

        return R.ok(RoleMenuEntity.class).setData( roleMenu);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("sys:rolemenu:save")
        @ApiOperation("保存角色系统表信息") 
	    public R<Object> save(@RequestBody RoleMenuEntity roleMenu){
			roleMenuService.save(roleMenu);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:rolemenu:update")
        @ApiOperation("修改角色系统表信息") 
	    public R<Object> update(@RequestBody RoleMenuEntity roleMenu){
			roleMenuService.updateById(roleMenu);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:rolemenu:delete")
        @ApiOperation("删除角色系统表信息") 
	    public R<Object> delete(@RequestBody String[] ids){
			roleMenuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
