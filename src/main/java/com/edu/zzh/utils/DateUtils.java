package com.edu.zzh.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: zzh
 * @Date: 2021/3/14 19:07
 * @Description:
 */
public class DateUtils {

    public static String getDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        return (df.format(new Date()));// new Date()为获取当前系统时间
    }

    public static String getDateTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return (df.format(new Date()));// new Date()为获取当前系统时间
    }
}
