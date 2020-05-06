package com.zp.common.security.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 处理忽略的路径
 * 2020年4月29日08:45:08
 * zhaipan
 */
public class AnonsUtils {

    private List<String> anons = new ArrayList<String>();
    public void add(String path){
        anons.add(path);

    }
    public void add(List<String> path){
        anons.addAll(path);
    }


    /**
     * 获取所有忽略的路径
     * @return
     */
    public List<String> excludePath(){
        return anons;
    }

}
