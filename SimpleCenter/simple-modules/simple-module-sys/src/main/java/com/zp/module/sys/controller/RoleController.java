package com.zp.module.sys.controller;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zp.api.sys.entity.*;
import com.zp.common.core.util.PagerUtil;
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

import com.zp.api.sys.entity.RoleEntity;

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
@Api(value = "角色表API", tags = {"角色表API-通用"})
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;



    @Autowired
    private MenuService meunService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private RoleSystemService roleSystemService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private AuthUtils authUtils;


    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation("角色表列表")
    @ApiResponse(code = 0, message = "查询成功", response = RoleEntity.class)
    public R list(RoleEntity Role, PagerUtil pagerUtil) {

        IPage<RoleEntity> pageData = roleService.queryPage(Role, pagerUtil);

        return R.ok().setData(pageData);
    }

    /**
     * 角色集合
     */
    @GetMapping("/listRoleAll")
    @ApiOperation("角色集合")
    @ApiResponse(code = 0, message = "查询成功", response = RoleEntity.class)
    public R listRoleAll() {
        List<RoleEntity> roleEntityList = roleService.list(new QueryWrapper<RoleEntity>().orderByDesc("create_date"));
        return R.ok().setData(roleEntityList);
    }





    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:role:info")
    @ApiOperation("根据ID获取角色表信息")
    @ApiResponse(code = 0, message = "查询成功", response = RoleEntity.class)
    public R<RoleEntity> info(@PathVariable("id") String id) {
        RoleEntity role = roleService.getById(id);



        return R.ok(RoleEntity.class).setData(role);
    }

    /**
     * 角色信息和对应的菜单
     */
    @GetMapping("/infoMenu/{id}")
    @ApiOperation("根据ID获取角色表信息")
    @ApiResponse(code = 0, message = "查询成功", response = RoleEntity.class)
    public R infoMenu(@PathVariable("id") String id) {
        RoleEntity role = roleService.getById(id);
        Map<String, Object> map = new HashMap<>();

        List<SystemEntity> systemEntitieList = systemService.treeMenu();
        List<String> systemIds = roleSystemService.getSystemIds(id);
        List<String> menuIds = roleMenuService.getMenuIds(id);

        map.put("role", role);
        map.put("systemEntitieList", systemEntitieList);
        map.put("systemIds", systemIds);
        map.put("menuIds", menuIds);

        return R.ok().setData(map);
    }

    /**
     * 菜单信息
     */
    @GetMapping("/menu")
    @ApiOperation("根据ID获取角色表信息")
    @ApiResponse(code = 0, message = "查询成功", response = RoleEntity.class)
    public R menu() {
        List<SystemEntity> systemEntities = systemService.treeMenu();
        return R.ok().setData(systemEntities);
    }


    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("sys:role:save")
    @ApiOperation("保存角色表信息")
    public R<Object> save(@RequestBody RoleEntity role) {

        String userId = authUtils.getUserId();
        Date date = new Date();
        role.setCreateId(userId);
        role.setCreateDate(date);
        role.setUpdateId(userId);
        role.setUpdateDate(date);


        roleService.saveAuth(role);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:role:update")
    @ApiOperation("修改角色表信息")
    public R<Object> update(@RequestBody RoleEntity role) {
        String userId = authUtils.getUserId();
        Date date = new Date();
        role.setUpdateId(userId);
        role.setUpdateDate(date);
        roleService.updateAuth(role);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    @ApiOperation("删除角色表信息")
    public R<Object> delete(@RequestBody String[] ids) {

        roleService.removeByMyIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 返回用户登录的信息
     */
    @GetMapping("/getLoginRole/{id}")
    @ApiOperation("根据ID获取登录用户信息")
    @ApiResponse(code = 0, message = "查询成功", response = UserEntity.class)
    public R<Set> getLoginUser(@PathVariable("id") String id) {
        Set<String> set = meunService.findByRoleId(id);
        return R.ok(Set.class).setData(set);
    }




}
