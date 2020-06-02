package com.zp.common.core.util;


import com.alibaba.fastjson.JSONObject;
import com.zp.common.core.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.hardware.CentralProcessor.TickType;
import oshi.software.os.*;

import oshi.util.Util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * 获取服务器信息
 * zhaipan
 * 2020年6月1日11:20:28
 */
public class ServerUtil {
    private static final int OSHI_WAIT_SECOND = 300;

    public static void main(String[] args){

        System.out.println(JSONObject.toJSONString(ServerUtil.init( )));


    }



    public static ServerVO init(){

        ServerVO serverVO = new ServerVO();

        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        //服务器
        ServerUtil.setSysInfo(serverVO);

        //cpu
        ServerUtil.setCpuInfo(hal.getProcessor(),serverVO);

        //内存
        ServerUtil.setMemInfo(hal.getMemory(),serverVO);

        //磁盘
        ServerUtil.setSysFiles(si.getOperatingSystem(),serverVO);

        return serverVO;
    }

    /**
     * 设置CPU信息
     */
    private static void setCpuInfo(CentralProcessor processor,ServerVO serverVO) {
        // CPU信息
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(OSHI_WAIT_SECOND);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
        long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
        long softirq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
        long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
        long cSys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
        long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
        long iowait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
        long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;

        serverVO.setCpuNum(processor.getLogicalProcessorCount());
        serverVO.setCpuTotal(Arith.round(Arith.mul(totalCpu, 100), 2));
        serverVO.setCpuSys(Arith.round(Arith.mul(Arith.div(cSys ,totalCpu), 100), 2));
        serverVO.setCpuUsed(Arith.round(Arith.mul(Arith.div(user ,totalCpu), 100), 2));
        serverVO.setCpuFree(Arith.round(Arith.mul(Arith.div(idle ,totalCpu), 100), 2));

    }

    /**
     * 设置内存信息
     */
    private static void setMemInfo(GlobalMemory memory,ServerVO serverVO) {
        serverVO.setMemTotal(Arith.div(memory.getTotal(), (1024 * 1024 * 1024), 2));
        serverVO.setMemUsed(Arith.div(memory.getTotal() - memory.getAvailable(), (1024 * 1024 * 1024), 2));
        serverVO.setMemFree(Arith.div(memory.getAvailable(), (1024 * 1024 * 1024), 2));
        serverVO.setMemUsePro(Arith.round(Arith.mul(Arith.div(serverVO.getMemUsed(),serverVO.getMemTotal()), 100), 2));

    }

    /**
     * 设置服务器信息
     */
    private static void setSysInfo(ServerVO serverVO) {

        String hostName = null;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


        String ipAdd = null;
        try {
            ipAdd = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            ipAdd = "127.0.0.1";
        }

        Properties props = System.getProperties();
        serverVO.setServerComputerName(hostName);
        serverVO.setServerComputerIp(ipAdd);
        serverVO.setServerOsName(props.getProperty("os.name"));
        serverVO.setServerOsArch(props.getProperty("os.arch"));

    }


    /**
     * 设置磁盘信息
     */
    private static void setSysFiles(OperatingSystem os,ServerVO serverVO) {
        FileSystem fileSystem = os.getFileSystem();
        List<OSFileStore> fileStores = fileSystem.getFileStores();
        for (OSFileStore fs : fileStores) {
            long free = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - free;
            SysFile sysFile = new SysFile();
            sysFile.setDirName(fs.getMount());
            sysFile.setSysTypeName(fs.getType());
            sysFile.setTypeName(fs.getName());
            sysFile.setTotal(convertFileSize(total));
            sysFile.setFree(convertFileSize(free));
            sysFile.setUsed(convertFileSize(used));
            sysFile.setUsage(Arith.mul(Arith.div(used, total, 4), 100));
            serverVO.getSysFileList().add(sysFile);
        }
    }

    /**
     * 字节转换
     *
     * @param size 字节大小
     * @return 转换后值
     */
    private static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            return String.format("%d B", size);
        }
    }
}
