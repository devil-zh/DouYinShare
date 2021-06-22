package com.edu.zzh.povo.dto;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: zzh
 * @Date: 2021/3/16 16:26
 * @Description:
 */
@Data
@Table(name="video_info")
public class VideoInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer videoId;
    private Integer shopId;
    private Integer activityId;
    private String shopName;
    private String activityName;
    private String videoUrl;
    private String createTime;
    private String flag;

}
