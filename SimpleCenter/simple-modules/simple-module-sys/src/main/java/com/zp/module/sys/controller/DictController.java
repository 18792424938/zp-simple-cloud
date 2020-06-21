package com.zp.module.sys.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zp.api.sys.enums.SysEnum;
import com.zp.common.config.util.PagerUtil;
import com.zp.common.log.annotation.SysLog;
import com.zp.common.log.annotation.SysModule;
import com.zp.common.security.annotation.RequiresPermissions;
import com.zp.common.security.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zp.api.sys.entity.DictEntity;
import com.zp.module.sys.service.DictService;

import com.zp.common.core.util.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

/**
 * 字典表
 *
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:26
 */
@RestController
@RequestMapping("sys/dict")
@Api(value = "字典表API", tags = {"字典表API-通用"})
public class DictController {
    @Autowired
    private DictService dictService;

    @Autowired
    private AuthUtils authUtils;


    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:dict:list")
    @ApiOperation("字典表列表")
    @ApiResponse(code = 0, message = "查询成功", response = DictEntity.class)
    public R list(DictEntity Dict, PagerUtil pagerUtil) {

        IPage<DictEntity> pageData = dictService.queryPage(Dict, pagerUtil);

        return R.ok().setData(pageData);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:dict:info")
    @ApiOperation("根据ID获取字典表信息")
    @ApiResponse(code = 0, message = "查询成功", response = DictEntity.class)
    public R<DictEntity> info(@PathVariable("id") String id) {
        DictEntity dict = dictService.getById(id);

        return R.ok(DictEntity.class).setData(dict);
    }

    /**
     * 保存
     */
    @SysLog(value = "字典表保存",system= SysModule.sys)
    @PostMapping("/save")
    @RequiresPermissions("sys:dict:save")
    @ApiOperation("保存字典表信息")
    public R<Object> save(@RequestBody DictEntity dict) {
        String userId = authUtils.getUserId();
        Date date = new Date();
        dict.setCreateId(userId);
        dict.setCreateDate(date);
        dict.setUpdateId(userId);
        dict.setUpdateDate(date);

        dictService.save(dict);

        return R.ok();
    }

    /**
     * 保存
     */
    @SysLog(value = "字典表批量保存",system=SysModule.sys)
    @PostMapping("/saveAll")
    @RequiresPermissions("sys:dict:save")
    @ApiOperation("保存字典表信息")
    public R<Object> saveAll(@RequestBody List<DictEntity> dictEntityList) {
        String userId = authUtils.getUserId();
        Date date = new Date();
        for (DictEntity dictEntity : dictEntityList) {
            dictEntity.setCreateId(userId);
            dictEntity.setCreateDate(date);
            dictEntity.setUpdateId(userId);
            dictEntity.setUpdateDate(date);
        }
        dictService.saveBatch(dictEntityList);
        return R.ok();
    }



    /**
     * 修改
     */
    @SysLog(value = "字典表修改",system=SysModule.sys)
    @PostMapping("/update")
    @RequiresPermissions("sys:dict:update")
    @ApiOperation("修改字典表信息")
    public R<Object> update(@RequestBody DictEntity dict) {
        String userId = authUtils.getUserId();
        Date date = new Date();
        dict.setUpdateId(userId);
        dict.setUpdateDate(date);
        dictService.updateById(dict);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog(value = "字典表删除",system=SysModule.sys)
    @PostMapping("/delete")
    @RequiresPermissions("sys:dict:delete")
    @ApiOperation("删除字典表信息")
    public R<Object> delete(@RequestBody String[] ids) {
        dictService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 根据类型返回所有的字典集合
     */
    @GetMapping("/types")
    @ApiOperation("根据类型返回所有的字典集合")
    @ApiResponse(code = 0, message = "查询成功", response = DictEntity.class)
    public R<List> type(String type) {


        QueryWrapper<DictEntity> dictEntityQueryWrapper = new QueryWrapper<>();
        dictEntityQueryWrapper.eq("type",type);
        dictEntityQueryWrapper.eq("status", SysEnum.DICTSTATUS_10.getCode());
        dictEntityQueryWrapper.orderByAsc("order_num");
        List<DictEntity> dictEntityList = dictService.list(dictEntityQueryWrapper);

        return R.ok(List.class).setData(dictEntityList);
    }

    /**
     * 根据类型和值 返回对应的实体
     */
    @GetMapping("/translateDict")
    @ApiOperation("根据类型和值 返回对应的实体")
    @ApiResponse(code = 0, message = "查询成功", response = DictEntity.class)
    public R<DictEntity> translateDict(String type,String code) {

        QueryWrapper<DictEntity> dictEntityQueryWrapper = new QueryWrapper<>();
        dictEntityQueryWrapper.eq("type",type);
        dictEntityQueryWrapper.eq("status", SysEnum.DICTSTATUS_10.getCode());
        dictEntityQueryWrapper.eq("code", code);

        DictEntity dictEntity = dictService.getOne(dictEntityQueryWrapper);

        return R.ok(DictEntity.class).setData(dictEntity);
    }




}
