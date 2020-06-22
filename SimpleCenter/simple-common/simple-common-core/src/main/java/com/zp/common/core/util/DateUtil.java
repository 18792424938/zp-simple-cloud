package com.zp.common.core.util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static Date parseDate(String date, String pattern){
        try {
            return DateUtils.parseDate(date,pattern);
        } catch (ParseException e) {
            return null;
        }
    }
    public static Date parseDate(String date){
        return parseDate(date,DateUtil.DEFAULT_PATTERN);
    }

    public static String formatData(Date date){
        return formatData(date,DEFAULT_PATTERN);
    }
    public static String formatData(Date date, String pattern){
        return new SimpleDateFormat(pattern).format(date);
    }




}
