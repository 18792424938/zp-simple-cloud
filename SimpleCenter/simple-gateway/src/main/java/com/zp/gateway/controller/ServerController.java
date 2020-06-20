package com.zp.gateway.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import com.zp.common.core.util.R;
import com.zp.common.core.util.RedisUtils;
import com.zp.common.core.vo.ServerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping("/gateway/server")
public class ServerController {

    @Resource(name ="eurekaClient")
    private EurekaClient eurekaClient;

    @Autowired
    private RedisUtils redisUtils;


    /**
     * 获取系统的信息
     * @return
     */
    @GetMapping("/list")
    public R list(){
        List serverList = redisUtils.get("serverList", List.class,300);
        if(serverList==null){
            serverList = new ArrayList();
        }
        return R.ok().setData(serverList);
    }

    /**
     * 定时初始化系统信息
     */
    @GetMapping("/init")
    public void init(){
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(5000);
        httpRequestFactory.setConnectTimeout(2000);
        httpRequestFactory.setReadTimeout(2000);

        RestTemplate restTemplate =  new RestTemplate(httpRequestFactory);

        Applications applications = eurekaClient.getApplications();


        List<ServerVO> list = new LinkedList<>();
        Set<String> set = new HashSet<>();

        for(Application application : applications.getRegisteredApplications()) {
            List<InstanceInfo> instances = application.getInstances();
            try {
                for (InstanceInfo instanceInfo : instances){
                    String rsp = restTemplate.getForEntity(instanceInfo.getHomePageUrl() + "base/info", String.class).getBody();
                    //使用fastjson进行解析
                    JSONObject apiObject = JSON.parseObject(rsp);
                    String code= apiObject.getString("code");
                    JSONObject data = apiObject.getJSONObject("data");
                    if("0".equals(code)&&data!=null){
                        ServerVO serverVO = data.toJavaObject(ServerVO.class);
                        String information = serverVO.getServerOsName()+serverVO.getServerComputerIp();
                        if(!set.contains(information)){
                            list.add(serverVO);
                            set.add(information);
                        }
                    }
                }
            }catch(Exception ex){

            }
        }
        redisUtils.set("serverList",list,300);
    }
}
