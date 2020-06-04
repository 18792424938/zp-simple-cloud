package com.zp.common.base.controller;

import com.zp.common.core.util.R;
import com.zp.common.core.util.ServerUtil;
import com.zp.common.core.vo.ServerVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.SystemInfo;

@RestController
@RequestMapping("/base")
public class BaseController {
    /**
     * 初始化当前应用所在在服务器参数
     * @return
     */
    @GetMapping("/info")
    public R info(){
        ServerVO serverVO = ServerUtil.init();
        return R.ok().setData(serverVO);

    }
}
