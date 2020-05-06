package com.zp.module.sys.controller;

import java.util.Arrays;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zp.api.sys.entity.UploadFileEntity;
import com.zp.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import com.zp.module.sys.service.UploadFileService;

import com.zp.common.core.util.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation; 
import io.swagger.annotations.ApiResponse; 

/**
 * 附件表
 *
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:25
 */
@RestController
@RequestMapping("sys/uploadfile")
@Api(value = "附件表API", tags = { "附件表API-通用" })
public class UploadFileController {
    @Autowired
    private UploadFileService uploadFileService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:uploadfile:list")
        @ApiOperation("附件表列表")
    @ApiResponse(code=0,message="查询成功",response=UploadFileEntity.class)
	    public R list(UploadFileEntity UploadFile , IPage<UploadFileEntity> page){

        IPage<UploadFileEntity> pageData = uploadFileService.queryPage(UploadFile , page);

        return R.ok().setData(pageData);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:uploadfile:info")
        @ApiOperation("根据ID获取附件表信息")
    @ApiResponse(code=0,message="查询成功",response=UploadFileEntity.class)
	    public R<UploadFileEntity> info(@PathVariable("id") String id){
			UploadFileEntity uploadFile = uploadFileService.getById(id);

        return R.ok(UploadFileEntity.class).setData( uploadFile);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("sys:uploadfile:save")
        @ApiOperation("保存附件表信息") 
	    public R<Object> save(@RequestBody UploadFileEntity uploadFile){
			uploadFileService.save(uploadFile);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:uploadfile:update")
        @ApiOperation("修改附件表信息") 
	    public R<Object> update(@RequestBody UploadFileEntity uploadFile){
			uploadFileService.updateById(uploadFile);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:uploadfile:delete")
        @ApiOperation("删除附件表信息") 
	    public R<Object> delete(@RequestBody String[] ids){
			uploadFileService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
