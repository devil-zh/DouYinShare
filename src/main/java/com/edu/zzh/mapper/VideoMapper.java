package com.edu.zzh.mapper;

import com.edu.zzh.povo.dto.CardInfo;
import com.edu.zzh.povo.dto.VideoInfo;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: zzh
 * @Date: 2021/3/16 15:18
 * @Description:
 */
public interface VideoMapper extends Mapper<VideoInfo> {

    @Select("<script>" + "select " +
            "video_id videoId,shop_id shopId,shop_name shopName,activity_id activityId,activity_name activityName,create_time createTime,video_url videoUrl " +
            " ,flag from video_info where 1=1" +
            "<if test=\"activityName != '' &amp;&amp; activityName!=null \"> and activity_name like CONCAT('%',#{activityName},'%') </if>" +
            "<if test=\"shopName != '' &amp;&amp; shopName != null\"> and shop_name like CONCAT('%',#{shopName},'%') </if>" +
            "<if test=\"flag != '' &amp;&amp; flag != null\"> and flag=#{flag} </if>" +
            "</script>"
    )
    List<VideoInfo> queryVideoListByPage(String activityName,String shopName,String flag);

    @Update("update video_info set flag='未使用' where video_url=#{videoUrl}")
    void updateByUrl(String videoUrl);
}
