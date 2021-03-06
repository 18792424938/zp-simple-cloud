package com.zp.module.sys.controller;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zp.api.sys.entity.MenuEntity;
import com.zp.api.sys.entity.RoleMenuEntity;
import com.zp.api.sys.entity.SystemEntity;
import com.zp.common.core.util.RedisUtils;
import com.zp.common.log.annotation.SysLog;
import com.zp.common.log.annotation.SysModule;
import com.zp.common.security.annotation.RequiresPermissions;
import com.zp.common.security.utils.AuthUtils;
import com.zp.module.sys.service.RoleMenuService;
import com.zp.module.sys.service.SystemService;
import com.zp.module.sys.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zp.api.sys.entity.MenuEntity;
import com.zp.module.sys.service.MenuService;

import com.zp.common.core.util.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

/**
 * 菜单表
 *
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:26
 */
@RestController
@RequestMapping("sys/menu")
@Api(value = "菜单表API", tags = {"菜单表API-通用"})
public class MenuController {
    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleMenuService roleMenuService;


    @Autowired
    private UserService userService;


    @Autowired
    private SystemService systemService;

    @Autowired
    private AuthUtils authUtils;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:menu:list")
    @ApiOperation("菜单表列表")
    @ApiResponse(code = 0, message = "查询成功", response = MenuEntity.class)
    public R list(MenuEntity Menu, IPage<MenuEntity> page) {

        IPage<MenuEntity> pageData = menuService.queryPage(Menu, page);

        return R.ok().setData(pageData);
    }

    /**
     * 树形菜单
     */
    @GetMapping("/tree/{systemId}")
    @ApiOperation("菜单表列表")
    @ApiResponse(code = 0, message = "查询成功", response = MenuEntity.class)
    public R tree(@PathVariable("systemId") String systemId) {
        List<MenuEntity> tree = menuService.tree(systemId);
        return R.ok().setData(tree);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @ApiOperation("根据ID获取菜单表信息")
    @ApiResponse(code = 0, message = "查询成功", response = MenuEntity.class)
    public R<MenuEntity> info(@PathVariable("id") String id) {
        MenuEntity menu = menuService.getById(id);
        if(StringUtils.isNotBlank(menu.getParentId())){
            MenuEntity menuP = menuService.getById(menu.getParentId());
            menu.setParentName(menuP.getName());
        }
        return R.ok(MenuEntity.class).setData(menu);
    }

    

    /**
     * 保存
     */
    @SysLog(value = "菜单保存",system= SysModule.sys)
    @PostMapping("/save")
    @RequiresPermissions("sys:menu:save")
    @ApiOperation("保存菜单表信息")
    public R<Object> save(@RequestBody MenuEntity menu) {
        String userId = authUtils.getUserId();
        Date date = new Date();
        menu.setCreateId(userId);
        menu.setCreateDate(date);
        menu.setUpdateId(userId);
        menu.setUpdateDate(date);
        menuService.save(menu);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog(value = "菜单修改",system=SysModule.sys)
    @PostMapping("/update")
    @RequiresPermissions("sys:menu:update")
    @ApiOperation("修改菜单表信息")
    public R<Object> update(@RequestBody MenuEntity menu) {
        String userId = authUtils.getUserId();
        Date date = new Date();

        menu.setUpdateId(userId);
        menu.setUpdateDate(date);

        menuService.updateById(menu);


        //处理缓存
        QueryWrapper<RoleMenuEntity> roleMenuEntityQueryWrapper = new QueryWrapper<>();
        roleMenuEntityQueryWrapper.eq("menu_id",menu.getId());
        List<RoleMenuEntity> list = roleMenuService.list(roleMenuEntityQueryWrapper);
        for (RoleMenuEntity roleMenuEntity : list) {
            redisUtils.del("requiresPermissions_"+roleMenuEntity.getRoleId());
        }
        return R.ok();
    }


    /**
     * 删除
     */
    @SysLog(value = "菜单删除",system=SysModule.sys)
    @GetMapping("/delete/{id}")
    @RequiresPermissions("sys:menu:delete")
    @ApiOperation("删除菜单表信息")
    public R<Object> delete(@PathVariable("id") String id) {

        QueryWrapper<MenuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);
        int count = menuService.count(queryWrapper);
        if (count>0){
            return R.error("请先删除子菜单");
        }

        menuService.deleteById(id);


        //处理缓存
        QueryWrapper<RoleMenuEntity> roleMenuEntityQueryWrapper = new QueryWrapper<>();
        roleMenuEntityQueryWrapper.eq("menu_id",id);
        List<RoleMenuEntity> list = roleMenuService.list(roleMenuEntityQueryWrapper);
        for (RoleMenuEntity roleMenuEntity : list) {
            redisUtils.del("requiresPermissions_"+roleMenuEntity.getRoleId());
        }

        return R.ok();
    }

    /**
     * 根据系统id返回对应菜单树
     */
    @GetMapping("/menuTree")
    @ApiOperation("根据ID获取菜单表信息")
    @ApiResponse(code = 0, message = "查询成功", response = MenuEntity.class)
    public R menuTree(String systemId) {
        List<MenuEntity> menuTree = menuService.tree(systemId);
        return R.ok().setData(menuTree);
    }

    /**
     * 菜单
     */
    @GetMapping("/nav")
    @ApiOperation("根据ID获取菜单表信息")
    @ApiResponse(code = 0, message = "查询成功", response = MenuEntity.class)
    public R nav(String systemId) {

        String userId = authUtils.getUserId();

        SystemEntity systemEntity = null;

        List<String> systemIds= userService.userSystemIds(userId);
        if (systemIds.contains(systemId)){
            systemEntity = systemService.getById(systemId);
        }else{
            systemEntity = userService.userSystem(userId);
        }

        if(systemEntity==null){
            return R.error("您没有任何权限,请联系管理员");
        }else{
            //查询该系统下的所有菜单
            List<MenuEntity> nav = menuService.nav(systemEntity.getId(),userId);
            systemEntity.setChildren(nav);
        }
        Set<String> userTokenPerms = authUtils.getUserTokenPerms(authUtils.getUserId());

        Map<String,Object> map = new HashMap<>();
        map.put("systemEntity",systemEntity);
        map.put("perms",userTokenPerms);
        return R.ok().setData(map);
    }


}
