package com.edu.zzh.mapper;

import com.edu.zzh.povo.dto.CardInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: zzh
 * @Date: 2021/3/16 15:18
 * @Description:
 */
public interface CardMapper extends Mapper<CardInfo> {

    @Select("<script>" +
            "select card_id cardId,card_name cardName,card_content cardContent,card_type cardType from card_info " +
            " where 1=1" +
            "<if test=\"cardType != '' &amp;&amp; cardType!=null \"> and card_type like CONCAT('%',#{cardType},'%') </if>" +
            "</script>")
    List<CardInfo> queryCardListByPage(@Param("cardType") String cardType);

    @Select("select card_id cardId,card_name cardName,card_content cardContent, card_type cardType from card_info")
    List<CardInfo> queryCardList();
    @Select("select distinct card_type cardType from card_info")
    List<CardInfo> queryCardTypeList();
}
