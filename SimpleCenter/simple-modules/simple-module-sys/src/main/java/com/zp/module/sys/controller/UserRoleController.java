package com.zp.module.sys.controller;

import java.util.Arrays;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zp.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zp.api.sys.entity.UserRoleEntity;
import com.zp.module.sys.service.UserRoleService;

import com.zp.common.core.util.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation; 
import io.swagger.annotations.ApiResponse; 

/**
 * 用户角色表
 *
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:24
 */
@RestController
@RequestMapping("sys/userrole")
@Api(value = "用户角色表API", tags = { "用户角色表API-通用" })
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:userrole:list")
        @ApiOperation("用户角色表列表")
    @ApiResponse(code=0,message="查询成功",response=UserRoleEntity.class)
	    public R list(UserRoleEntity UserRole , IPage<UserRoleEntity> page){
       
        IPage<UserRoleEntity> pageData = userRoleService.queryPage(UserRole , page);

        return R.ok().setData(pageData);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:userrole:info")
        @ApiOperation("根据ID获取用户角色表信息")
    @ApiResponse(code=0,message="查询成功",response=UserRoleEntity.class)
	    public R<UserRoleEntity> info(@PathVariable("id") String id){
			UserRoleEntity userRole = userRoleService.getById(id);

        return R.ok(UserRoleEntity.class).setData( userRole);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("sys:userrole:save")
        @ApiOperation("保存用户角色表信息") 
	    public R<Object> save(@RequestBody UserRoleEntity userRole){
			userRoleService.save(userRole);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:userrole:update")
        @ApiOperation("修改用户角色表信息") 
	    public R<Object> update(@RequestBody UserRoleEntity userRole){
			userRoleService.updateById(userRole);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:userrole:delete")
        @ApiOperation("删除用户角色表信息") 
	    public R<Object> delete(@RequestBody String[] ids){
			userRoleService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
