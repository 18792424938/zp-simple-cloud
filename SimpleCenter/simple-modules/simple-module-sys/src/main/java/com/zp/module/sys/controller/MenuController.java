package com.zp.module.sys.controller;

import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zp.api.sys.entity.MenuEntity;
import com.zp.common.security.annotation.RequiresPermissions;
import com.zp.common.security.utils.AuthUtils;
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
@Api(value = "菜单表API", tags = { "菜单表API-通用" })
public class MenuController {
    @Autowired
    private MenuService menuService;

    @Autowired
    private AuthUtils authUtils;
    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:menu:list")
        @ApiOperation("菜单表列表")
    @ApiResponse(code=0,message="查询成功",response=MenuEntity.class)
	    public R list(MenuEntity Menu , IPage<MenuEntity> page){

        IPage<MenuEntity> pageData = menuService.queryPage(Menu , page);

        return R.ok().setData(pageData);
    }

    /**
     * 树形菜单
     */
    @GetMapping("/tree")
    @ApiOperation("菜单表列表")
    @ApiResponse(code=0,message="查询成功",response=MenuEntity.class)
    public R tree(){

        List<MenuEntity> tree = menuService.tree();

        return R.ok().setData(tree);
    }




    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:menu:info")
        @ApiOperation("根据ID获取菜单表信息")
    @ApiResponse(code=0,message="查询成功",response=MenuEntity.class)
	    public R<MenuEntity> info(@PathVariable("id") String id){
			MenuEntity menu = menuService.getById(id);

        return R.ok(MenuEntity.class).setData( menu);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("sys:menu:save")
        @ApiOperation("保存菜单表信息") 
	    public R<Object> save(@RequestBody MenuEntity menu){
			menuService.save(menu);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:menu:update")
        @ApiOperation("修改菜单表信息") 
	    public R<Object> update(@RequestBody MenuEntity menu){
			menuService.updateById(menu);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:menu:delete")
        @ApiOperation("删除菜单表信息") 
	    public R<Object> delete(@RequestBody String[] ids){
			menuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }



    /**
     * 菜单
     */
    @GetMapping("/nav")
    @ApiOperation("根据ID获取菜单表信息")
    @ApiResponse(code=0,message="查询成功",response=MenuEntity.class)
    public R<List> nav(String systemId){



        return R.ok(List.class).setData(null);
    }


}
