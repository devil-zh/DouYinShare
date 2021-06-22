package com.edu.zzh.povo.dto;

import lombok.Data;

import javax.persistence.Table;

/**
 * @Author: zzh
 * @Date: 2021/4/14 17:43
 * @Description:
 */
@Data
@Table(name="admin")
public class Admin {
    private String username;
    private String password;
    private String salt;
}
