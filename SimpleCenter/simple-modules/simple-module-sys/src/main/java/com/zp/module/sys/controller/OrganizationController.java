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

import com.zp.api.sys.entity.OrganizationEntity;
import com.zp.module.sys.service.OrganizationService;

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
@RequestMapping("sys/organization")
@Api(value = "角色表API", tags = { "角色表API-通用" })
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:organization:list")
        @ApiOperation("角色表列表")
    @ApiResponse(code=0,message="查询成功",response=OrganizationEntity.class)
	    public R list(OrganizationEntity Organization , IPage<OrganizationEntity> page){

        IPage<OrganizationEntity> pageData = organizationService.queryPage(Organization , page);

        return R.ok().setData(pageData);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:organization:info")
        @ApiOperation("根据ID获取角色表信息")
    @ApiResponse(code=0,message="查询成功",response=OrganizationEntity.class)
	    public R<OrganizationEntity> info(@PathVariable("id") String id){
			OrganizationEntity organization = organizationService.getById(id);

        return R.ok(OrganizationEntity.class).setData( organization);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("sys:organization:save")
        @ApiOperation("保存角色表信息") 
	    public R<Object> save(@RequestBody OrganizationEntity organization){
			organizationService.save(organization);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:organization:update")
        @ApiOperation("修改角色表信息") 
	    public R<Object> update(@RequestBody OrganizationEntity organization){
			organizationService.updateById(organization);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:organization:delete")
        @ApiOperation("删除角色表信息") 
	    public R<Object> delete(@RequestBody String[] ids){
			organizationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
