package com.edu.zzh.service;

import com.edu.zzh.exception.MyException;
import com.edu.zzh.exception.MyExceptionEnum;
import com.edu.zzh.mapper.ActivityMapper;
import com.edu.zzh.mapper.ShopMapper;
import com.edu.zzh.mapper.VideoMapper;
import com.edu.zzh.povo.dto.*;
import com.edu.zzh.povo.vo.PageResult;
import com.edu.zzh.utils.DateUtils;
import com.edu.zzh.utils.DouYinUpload;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

/**
 * @Author: zzh
 * @Date: 2021/3/13 13:02
 * @Description:
 */
@Service
public class ActivityService {
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private CardService cardService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserCardService userCardService;
    @Autowired
    private VideoService videoService;

    public PageResult<ActivityInfo> queryActivityListByPage(Integer pageNum, Integer pageSize, String activityStatus, String activityName,Integer shopId) {

        //分页
        PageHelper.startPage(pageNum, pageSize);

        //查询
        List<ActivityInfo> activityInfoList = activityMapper.queryActivityListByPage(activityStatus,activityName,shopId);
        /*if (CollectionUtils.isEmpty(activityInfoList))
            throw new MyException(MyExceptionEnum.ACTIVITY_NOT_FOUND);*/
        for (ActivityInfo activityInfo:activityInfoList){
            if (Integer.parseInt(activityInfo.getActivityStatus())==0){
                activityInfo.setActivityStatus("已结束");
            } else {
                activityInfo.setActivityStatus("进行中");
            }
        }
        PageInfo<ActivityInfo> pageInfo = new PageInfo<>(activityInfoList);
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }
    public void deleteActivityList(List<ActivityInfo> activityList) {
        for (ActivityInfo activityInfo:activityList){
            ActivityInfo activity = activityMapper.selectByPrimaryKey(activityInfo.getActivityId());
            videoService.updateVideoFlag(activity.getVideoUrl());
            activityMapper.deleteByPrimaryKey(activityInfo.getActivityId());
        }
    }

    public void deleteActivity(ActivityInfo activityInfo) {
        ActivityInfo activity = activityMapper.selectByPrimaryKey(activityInfo.getActivityId());
        videoService.updateVideoFlag(activity.getVideoUrl());
        activityMapper.deleteByPrimaryKey(activityInfo.getActivityId());
    }

    public void saveActivity(ActivityInfo activityInfo) {
        CardInfo cardInfo = cardService.queryCardById(activityInfo.getCardId());
        activityInfo.setCardName(cardInfo.getCardName());
        activityInfo.setActivityStatus("1");
        activityInfo.setUsers(0);
        activityInfo.setCreateDate(DateUtils.getDateTime());
        activityMapper.insert(activityInfo);
    }


    public void updateVideoUrl(String videoUrl,Integer activityId) {
        int count = activityMapper.updateVideoUrl(videoUrl,activityId);
        if (count==0)
            throw new MyException(MyExceptionEnum.FILE_UPLOAD_ERROR);
    }

    public void updateActivityStatus(ActivityInfo activityInfo) {
        activityMapper.updateActivityStatus(activityInfo.getActivityId());
    }

    public ActivityInfo queryActivityById(Integer activityId) {
        return  activityMapper.selectByPrimaryKey(activityId);
    }

    public void saveEditActivity(ActivityInfo activityInfo) {
        Integer activityId = activityInfo.getActivityId();
        Integer shopId = activityInfo.getShopId();
        String activityName = activityInfo.getActivityName();
        String createDate = activityInfo.getCreateDate();
        String expDate = activityInfo.getExpDate();
        String introduce = activityInfo.getActivityIntroduce();
        String activityPwd = activityInfo.getActivityPwd();
        Integer cardId = activityInfo.getCardId();
        CardInfo cardInfo = cardService.queryCardById(cardId);
        String cardName = cardInfo.getCardName();
        activityMapper.updateById(activityId,shopId,activityName,createDate,expDate,introduce,activityPwd,cardId,cardName);
    }

    public void addUserCard(String openid, Integer activityId) {
        UserInfo userInfo = userService.selectByOpenId(openid);
        ActivityInfo activityInfo = activityMapper.selectByPrimaryKey(activityId);
        Integer cardId = userCardService.selectCardById(activityId,userInfo.getUserId());
        if (cardId!=null)
            throw new MyException(MyExceptionEnum.CARD_EXISTS_TODAY);
        UserCard userCard = new UserCard();
        userCard.setUserId(userInfo.getUserId());
        userCard.setActivityId(activityId);
        userCard.setCreateTime(DateUtils.getDateTime());
        userCard.setStatus(1);
        userCard.setCardName(activityInfo.getCardName());
        userCardService.addUserCard(userCard);
    }

    public void usersAdd(Integer activityId) throws IOException {
        activityMapper.usersAdd(activityId);

    }

    public PageResult<ActivityInfo> queryShopListById(Integer pageNum, Integer pageSize, String activityStatus, String activityName, Integer shopId) {
        //分页
        PageHelper.startPage(pageNum, pageSize);

        //查询
        List<ActivityInfo> activityInfoList = activityMapper.queryActivityListById(activityStatus,activityName,shopId);
        /*if (CollectionUtils.isEmpty(activityInfoList))
            throw new MyException(MyExceptionEnum.ACTIVITY_NOT_FOUND);*/
        for (ActivityInfo activityInfo:activityInfoList){
            if (Integer.parseInt(activityInfo.getActivityStatus())==0){
                activityInfo.setActivityStatus("已结束");
            } else {
                activityInfo.setActivityStatus("进行中");
            }
        }
        PageInfo<ActivityInfo> pageInfo = new PageInfo<>(activityInfoList);
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }
}
