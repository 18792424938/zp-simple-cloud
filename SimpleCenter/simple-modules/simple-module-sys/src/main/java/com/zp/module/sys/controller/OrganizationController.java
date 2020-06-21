package com.zp.module.sys.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zp.common.log.annotation.SysLog;
import com.zp.common.log.annotation.SysModule;
import com.zp.common.security.annotation.RequiresPermissions;
import com.zp.common.security.utils.AuthUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
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
@Api(value = "角色表API", tags = {"角色表API-通用"})
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private AuthUtils authUtils;



    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:organization:list")
    @ApiOperation("角色表列表")
    @ApiResponse(code = 0, message = "查询成功", response = OrganizationEntity.class)
    public R list() {
        List<OrganizationEntity> list = organizationService.tree();
        return R.ok().setData(list);
    }

    @GetMapping("/tree")
    @ApiOperation("组织机构表列表")
    @ApiResponse(code = 0, message = "查询成功", response = OrganizationEntity.class)
    public R tree() {
        List<OrganizationEntity> list = organizationService.tree();
        return R.ok().setData(list);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @ApiOperation("根据ID获取角色表信息")
    @ApiResponse(code = 0, message = "查询成功", response = OrganizationEntity.class)
    public R<OrganizationEntity> info(@PathVariable("id") String id) {
        OrganizationEntity organization = organizationService.getById(id);
        if(StringUtils.isNotBlank(organization.getParentId())){
            OrganizationEntity organizationEntity = organizationService.getById(organization.getParentId());
            organization.setParentName(organizationEntity.getName());
        }

        return R.ok(OrganizationEntity.class).setData(organization);
    }

    /**
     * 保存
     */
    @SysLog(value = "组织机构保存",system= SysModule.sys)
    @PostMapping("/save")
    @RequiresPermissions("sys:organization:save")
    @ApiOperation("保存角色表信息")
    public R<Object> save(@RequestBody OrganizationEntity organization) {
        String userId = authUtils.getUserId();
        Date date = new Date();
        organization.setCreateId(userId);
        organization.setCreateDate(date);
        organization.setUpdateId(userId);
        organization.setUpdateDate(date);

        organizationService.save(organization);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog(value = "组织机构修改",system=SysModule.sys)
    @PostMapping("/update")
    @RequiresPermissions("sys:organization:update")
    @ApiOperation("修改角色表信息")
    public R<Object> update(@RequestBody OrganizationEntity organization) {
        String userId = authUtils.getUserId();
        Date date = new Date();
        organization.setUpdateId(userId);
        organization.setUpdateDate(date);
        organizationService.updateById(organization);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog(value = "组织机构删除",system=SysModule.sys)
    @GetMapping("/delete/{id}")
    @RequiresPermissions("sys:organization:delete")
    @ApiOperation("删除角色表信息")
    public R<Object> delete(@PathVariable("id") String id) {
        OrganizationEntity organizationEntity = organizationService.getById(id);
        if(StringUtils.isBlank(organizationEntity.getParentId())){
            return R.error("根组织不能删除");
        }

        QueryWrapper<OrganizationEntity> organizationEntityQueryWrapper = new QueryWrapper<>();
        organizationEntityQueryWrapper.eq("parent_id",id);
        int count = organizationService.count(organizationEntityQueryWrapper);
        if(count==0){
            organizationService.removeById(id);
        }else{
            return R.error("请先删除子组织");
        }
        return R.ok();
    }

}
