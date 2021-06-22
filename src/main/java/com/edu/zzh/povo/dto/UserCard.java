package com.edu.zzh.povo.dto;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: zzh
 * @Date: 2021/3/17 13:54
 * @Description:
 */
@Data
@Table(name="user_card")
public class UserCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    private Integer activityId;
    private String cardName;
    private Integer status;
    private String createTime;
    private String useTime;


}
