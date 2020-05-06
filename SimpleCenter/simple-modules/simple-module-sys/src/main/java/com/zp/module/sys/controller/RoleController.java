package com.zp.module.sys.controller;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zp.api.sys.entity.RoleEntity;
import com.zp.api.sys.entity.UserEntity;
import com.zp.api.sys.entity.UserRoleEntity;
import com.zp.common.security.annotation.RequiresPermissions;
import com.zp.module.sys.service.MenuService;
import com.zp.module.sys.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zp.api.sys.entity.RoleEntity;
import com.zp.module.sys.service.RoleService;

import com.zp.common.core.util.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation; 
import io.swagger.annotations.ApiResponse; 

/**
 * 角色表
 *
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:25
 */
@RestController
@RequestMapping("sys/role")
@Api(value = "角色表API", tags = { "角色表API-通用" })
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService meunService;



    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:role:list")
        @ApiOperation("角色表列表")
    @ApiResponse(code=0,message="查询成功",response=RoleEntity.class)
	    public R list(RoleEntity Role , IPage<RoleEntity> page){

        IPage<RoleEntity> pageData = roleService.queryPage(Role , page);

        return R.ok().setData(pageData);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:role:info")
        @ApiOperation("根据ID获取角色表信息")
    @ApiResponse(code=0,message="查询成功",response=RoleEntity.class)
	    public R<RoleEntity> info(@PathVariable("id") String id){
			RoleEntity role = roleService.getById(id);

        return R.ok(RoleEntity.class).setData( role);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("sys:role:save")
        @ApiOperation("保存角色表信息") 
	    public R<Object> save(@RequestBody RoleEntity role){
			roleService.save(role);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:role:update")
        @ApiOperation("修改角色表信息") 
	    public R<Object> update(@RequestBody RoleEntity role){
			roleService.updateById(role);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:role:delete")
        @ApiOperation("删除角色表信息") 
	    public R<Object> delete(@RequestBody String[] ids){
			roleService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


    /**
     * 返回用户登录的信息
     *
     */
    @PostMapping("/getLoginRole")
    @ApiOperation("根据ID获取登录用户信息")
    @ApiResponse(code=0,message="查询成功",response= UserEntity.class)
    public R<Map> getLoginUser(@RequestBody List<String> ids){
        Map<String, Set<String>> map = new HashMap<>();


        for (String id : ids) {
            //查询该角色对应的所有权限码
            map.put(id,meunService.findByRoleId(id));
        }
        return R.ok(Map.class).setData(map);
    }


}
