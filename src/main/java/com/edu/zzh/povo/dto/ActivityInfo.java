package com.edu.zzh.povo.dto;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: zzh
 * @Date: 2021/3/13 13:05
 * @Description:
 */
@Data
@Table(name="activity_info")
public class ActivityInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activityId;
    private Integer shopId;
    private Integer cardId;
    private String activityName;
    private String createDate;
    private String expDate;
    private Integer users;
    private String activityStatus;
    private String cardName;
    private String activityPwd;
    private String activityIntroduce;
    private String videoUrl;

}
