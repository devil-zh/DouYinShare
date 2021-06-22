package com.edu.zzh.config;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Author: zzh
 * @Date: 2021/3/15 14:57
 * @Description:
 */
@Data
@Component
public class DouYinProperties {
    private String baseUrl="https://open.douyin.com";
    private String scope="video.create,user_info,mobile";
    private String redirect_uri="http://zh.congliankeji.com/lookVideo?activityId=";
}
