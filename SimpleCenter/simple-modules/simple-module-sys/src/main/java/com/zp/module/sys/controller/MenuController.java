package com.zp.module.sys.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zp.api.sys.entity.MenuEntity;
import com.zp.api.sys.entity.SystemEntity;
import com.zp.common.security.annotation.RequiresPermissions;
import com.zp.common.security.utils.AuthUtils;
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
    private UserService userService;


    @Autowired
    private SystemService systemService;

    @Autowired
    private AuthUtils authUtils;

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
    @PostMapping("/update")
    @RequiresPermissions("sys:menu:update")
    @ApiOperation("修改菜单表信息")
    public R<Object> update(@RequestBody MenuEntity menu) {
        String userId = authUtils.getUserId();
        Date date = new Date();

        menu.setUpdateId(userId);
        menu.setUpdateDate(date);

        menuService.updateById(menu);

        return R.ok();
    }


    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys:menu:delete")
    @ApiOperation("删除菜单表信息")
    public R<Object> delete(@PathVariable("id") String id) {

        QueryWrapper<MenuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);
        int count = menuService.count();
        if (count>0){
            return R.error("请先删除子菜单");
        }

        menuService.deleteById(id);

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

        if (StringUtils.isNotBlank(systemId)) {
            systemEntity = systemService.getById(systemId);
        }
        if(systemEntity==null){
            systemEntity = userService.userSystem(userId);
        }

        if(systemEntity==null){
            return R.error("您没有任何权限,请联系管理员");
        }else{
            //查询该系统下的所有菜单
            List<MenuEntity> nav = menuService.nav(systemEntity.getId());
            systemEntity.setChildren(nav);
        }
        return R.ok().setData(systemEntity);
    }


}
