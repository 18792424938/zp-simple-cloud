package com.zp.module.log.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zp.api.log.entity.LoginLogEntity;
import com.zp.common.config.util.PagerUtil;
import com.zp.common.core.util.R;
import com.zp.common.security.annotation.RequiresPermissions;
import com.zp.common.security.utils.AuthUtils;
import com.zp.module.log.service.LoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 日志
 *
 * @author zp
 * @email 18792424938@163.com
 * @date 2020-04-24 21:01:26
 */
@RestController
@RequestMapping("log/loginLog")
@Api(value = "字典表API", tags = {"字典表API-通用"})
public class LoginLogController {
    @Autowired
    private LoginLogService logService;

    @Autowired
    private AuthUtils authUtils;





    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:log:list")
    @ApiOperation("字典表列表")
    @ApiResponse(code = 0, message = "查询成功", response = LoginLogEntity.class)
    public R list(LoginLogEntity Log, PagerUtil pagerUtil) {

        IPage<LoginLogEntity> pageData = logService.queryPage(Log, pagerUtil);

        return R.ok().setData(pageData);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:log:info")
    @ApiOperation("根据ID获取字典表信息")
    @ApiResponse(code = 0, message = "查询成功", response = LoginLogEntity.class)
    public R<LoginLogEntity> info(@PathVariable("id") String id) {
        LoginLogEntity log = logService.getById(Long.valueOf(id));

        return R.ok(LoginLogEntity.class).setData(log);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("sys:log:save")
    @ApiOperation("保存字典表信息")
    public R<Object> save(@RequestBody LoginLogEntity log) {
        Date date = new Date();
        log.setCreateDate(date);

        logService.saveInfo(log);

        return R.ok();
    }

}
