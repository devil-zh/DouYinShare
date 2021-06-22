package com.edu.zzh.mapper;

import com.edu.zzh.povo.dto.CardInfo;
import com.edu.zzh.povo.dto.UserCard;
import com.edu.zzh.povo.vo.ActivityData;
import com.edu.zzh.povo.vo.PageResult;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: zzh
 * @Date: 2021/3/16 15:18
 * @Description:
 */
public interface UserCardMapper extends Mapper<UserCard> {
    @Select("select id,card_name cardName,create_time createTime from user_card where status=1 and substr(create_time,1,10)=#{today}" +
            "and user_id=#{userId} and activity_id=#{activityId}")
    UserCard queryUserCard(Integer userId, Integer activityId,String today);

    @Update("update user_card set status=0,use_time=#{useTime} where id=#{id}")
    void updateCardStatusById(Integer id,String useTime);

    @Select("select count(1) totalCards," +
            "count(case when status=0 then id end)usedCards," +
            "count(case when status=1 then id end)unusedlCards" +
            " from user_card where activity_id=#{activityId}")
    ActivityData selectActivityDataById(Integer activityId);

    @Select("select id from user_card where activity_id=#{activityId} and user_id=#{userId} and substr(create_time,1,10)=#{today}")
    Integer selectCardById(Integer activityId, Integer userId,String today);
}
