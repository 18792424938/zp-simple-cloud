package com.zp.common.core.vo;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class ServerVO {

    /**
     * 服务器名称
     */
    private String serverComputerName;
    /**
     * 服务器Ip
     */
    private String serverComputerIp;
    /**
     * 操作系统
     */
    private String serverOsName;
    /**
     * 系统架构
     */
    private String serverOsArch;




    /**
     * 核心数
     */
    private int cpuNum;

    /**
     * CPU总的使用率
     */
    private double cpuTotal;

    /**
     * CPU系统使用率
     */
    private double cpuSys;

    /**
     * CPU用户使用率
     */
    private double cpuUsed;
    /**
     * CPU当前空闲率
     */
    private double cpuFree;




    /**
     * 内存总量
     */
    private double memTotal;

    /**
     * 已用内存
     */
    private double memUsed;

    /**
     * 剩余内存
     */
    private double memFree;

    /**
     * 内存使用率
     */
    private double memUsePro;


    List<SysFile> sysFileList = new LinkedList<>();




}
