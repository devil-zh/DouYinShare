package com.edu.zzh.mapper;

import com.edu.zzh.povo.dto.ShopInfo;
import com.edu.zzh.povo.dto.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: zzh
 * @Date: 2021/3/8 15:01
 * @Description:
 */
public interface ShopMapper extends Mapper<ShopInfo> {


    @Select("<script>" +
            "select shop_id shopId,shop_name shopName,real_name realName,msisdn,regtime,exptime,salt,prov,city,region,sex,address,activity_cnt activityCnt" +
            " from shop_info " +
            "where 1=1" +
            "<if test=\"shopName != '' &amp;&amp; shopName!=null \"> and shop_name like CONCAT('%',#{shopName},'%')  </if>" +
            "<if test=\"regtime != '' &amp;&amp; regtime != null\"> and substr(regtime,1,10)=#{regtime} </if>" +
            "<if test=\"address != '' &amp;&amp; address!=null \"> and address like CONCAT('%',#{address},'%')  </if>" +
            "</script>")
    List<ShopInfo> queryShopListByPage(@Param("shopName") String shopName, @Param("regtime")String regtime, @Param("address") String address);

    @Update("update shop_info set activity_cnt=#{activityCnt} where shop_id=#{shopId}")
    void updateActivityCnt(Integer shopId,Integer activityCnt);
}
