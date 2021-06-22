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
@Table(name="card_info")
public class CardInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cardId;
    private String cardName;
    private String cardContent;
    private String cardType;
}
