package com.edu.zzh.service;

import com.edu.zzh.mapper.UserCardMapper;
import com.edu.zzh.povo.dto.CardInfo;
import com.edu.zzh.povo.dto.UserCard;
import com.edu.zzh.povo.dto.UserInfo;
import com.edu.zzh.povo.vo.ActivityData;
import com.edu.zzh.povo.vo.PageResult;
import com.edu.zzh.utils.DateUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zzh
 * @Date: 2021/3/17 14:00
 * @Description:
 */
@Service
public class UserCardService {
    @Autowired
    private UserCardMapper userCardMapper;
    @Autowired
    private UserService userService;

    /*领取卡券*/
    public void addUserCard(UserCard userCard) {
        userCardMapper.insert(userCard);
    }

   /* *//*按条件查询*//*
    public PageResult<UserCard> queryUserCardList(Integer pageNum, Integer pageSize,String openid, Integer activityId) {
        UserInfo userInfo = userService.selectByOpenId(openid);
        //分页
        PageHelper.startPage(pageNum, pageSize);
        List<UserCard> userCardList = userCardMapper.queryUserCardList(userInfo.getUserId(),activityId, DateUtils.getDate());
        PageInfo<UserCard> pageInfo = new PageInfo<>(userCardList);
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }*/
   /*按条件查询*/
   public UserCard queryUserCardList(String openid, Integer activityId) {
       UserInfo userInfo = userService.selectByOpenId(openid);
       UserCard userCard = userCardMapper.queryUserCard(userInfo.getUserId(),activityId, DateUtils.getDate());
       return userCard;
   }
    /*使用*/
    public void updateCardStatus(Integer id) {
        userCardMapper.updateCardStatusById(id,DateUtils.getDateTime());
    }

    /*查询活动数据  卡券使用情况*/
    public ActivityData selectActivityDataById(Integer activityId) {
        ActivityData activityData = userCardMapper.selectActivityDataById(activityId);
        return  activityData;
    }

    /*查询个人已领取卡券*/
    public Integer selectCardById(Integer activityId, Integer userId) {
        return userCardMapper.selectCardById(activityId,userId,DateUtils.getDate());
    }
}
