package com.edu.zzh.povo.dto;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: zzh
 * @Date: 2021/3/8 14:48
 * @Description:
 */
@Data
@Table(name="user_info")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String userName;
    private String userImage;
    private String msisdn;
    private String regtime;
    private String openid;
    private String prov;
    private String city;
    private String sex;
    private String salt;//加密的key
    private String accessToken;
}
