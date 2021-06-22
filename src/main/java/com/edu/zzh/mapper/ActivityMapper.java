package com.edu.zzh.mapper;

import com.edu.zzh.povo.dto.ActivityInfo;
import com.edu.zzh.povo.dto.ShopInfo;
import com.edu.zzh.povo.dto.UserCard;
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
public interface ActivityMapper extends Mapper<ActivityInfo> {


    @Select("<script>" +
            "select activity_id activityId,shop_id shopId,activity_name activityName,create_date creataDate,exp_date expDate,activity_status activityStatus, " +
            "card_name cardName,users" +
            " from activity_info " +
            "where 1=1" +
            "<if test=\"activityName != '' &amp;&amp; activityName!=null \"> and activity_name like CONCAT('%',#{activityName},'%') </if>" +
            "<if test=\"activityStatus != '' &amp;&amp; activityStatus != null\"> and activity_status=#{activityStatus} </if>" +
            "<if test=\"shopId != '' &amp;&amp; shopId != null\"> and shop_id=#{shopId} </if>" +
            "</script>")
    List<ActivityInfo> queryActivityListByPage(@Param("activityStatus") String activityStatus, @Param("activityName") String activityName,@Param("shopId") Integer shopId);

    @Update("update activity_info set video_url=#{videoUrl} where activity_id=#{activityId}")
    int updateVideoUrl(String videoUrl,Integer activityId);

    @Update("update activity_info set activity_status=0 where activity_id=#{activityId}")
    void updateActivityStatus(Integer activityId);

    @Update("update activity_info set shop_id=#{shopId},activity_name=#{activityName},create_date=#{createDate},exp_date=#{expDate}" +
            ",activity_introduce=#{introduce},activity_pwd=#{activityPwd},card_id=#{cardId},card_name=#{cardName} where activity_id=#{activityId}")
    void updateById(Integer activityId,Integer shopId, String activityName, String createDate, String expDate, String introduce,
                    String activityPwd, Integer cardId, String cardName);

    @Update("update activity_info set users=users+1 where activity_id=#{activityId}")
    void usersAdd(Integer activityId);

    @Select("<script>" +
            "select activity_id activityId,shop_id shopId,activity_name activityName,create_date creataDate,exp_date expDate,activity_status activityStatus, " +
            "card_name cardName,users" +
            " from activity_info " +
            "where shop_id=#{shopId}" +
            "<if test=\"activityName != '' &amp;&amp; activityName!=null \"> and activity_name like CONCAT('%',#{activityName},'%') </if>" +
            "<if test=\"activityStatus != '' &amp;&amp; activityStatus != null\"> and activity_status=#{activityStatus} </if>" +
            "</script>")
    List<ActivityInfo> queryActivityListById(String activityStatus, String activityName, Integer shopId);
}
