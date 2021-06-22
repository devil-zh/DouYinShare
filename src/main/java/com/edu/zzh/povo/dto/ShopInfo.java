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
@Table(name="shop_info")
public class ShopInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shopId;
    private String shopName;
    private String realName;
    private String sex;
    private String msisdn;
    private String regtime;
    private String exptime;
    private Integer activityCnt;
    private String prov;
    private String city;
    private String region;
    private String address;
    private String salt;

}
