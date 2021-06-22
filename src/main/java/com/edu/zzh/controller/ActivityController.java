package com.edu.zzh.controller;

import com.alibaba.fastjson.JSONObject;
import com.edu.zzh.exception.MyException;
import com.edu.zzh.exception.MyExceptionEnum;
import com.edu.zzh.povo.dto.*;
import com.edu.zzh.povo.vo.ActivityData;
import com.edu.zzh.povo.vo.PageResult;
import com.edu.zzh.service.*;
import com.edu.zzh.utils.DateUtils;
import com.edu.zzh.utils.DouYinUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @Author: zzh
 * @Date: 2021/3/13 12:41
 * @Description:
 */
@Controller
public class ActivityController {
    @Autowired
    private ActivityService activityService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private UserCardService userCardService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private CardService cardService;
    @Autowired
    private UserService userService;


    @RequestMapping("/queryActivityListByPage")
    public ResponseEntity queryActivityListByPage(Integer pageNum, Integer pageSize, String activityStatus, String activityName,Integer shopId){

        PageResult<ActivityInfo> activityListByPage= activityService.queryActivityListByPage(pageNum,pageSize,activityStatus,activityName,shopId);

        return ResponseEntity.ok(activityListByPage);
    }
    @DeleteMapping("/deleteActivityList")
    public ResponseEntity delActivityListById(@RequestBody List<ActivityInfo> activityList){
        activityService.deleteActivityList(activityList);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/deleteActivity")
    public ResponseEntity delActivityById(ActivityInfo activityInfo){
        activityService.deleteActivity(activityInfo);
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/saveActivity")
    public ResponseEntity saveActivity(@RequestBody ActivityInfo activityInfo) throws Exception {
        ShopInfo shopInfo = shopService.queryShopById(activityInfo.getShopId());
        if (shopInfo.getExptime().compareTo(DateUtils.getDate())<=0){
            throw  new MyException(MyExceptionEnum.SHOP_EXP_DATE);
        }else if (shopInfo.getActivityCnt()==0){
            throw new MyException(MyExceptionEnum.ACTIVITY_CNT_IS_ZERO);
        }else {
            shopService.updateCtivityCnt(shopInfo.getShopId(),shopInfo.getActivityCnt()-1);
        }
        if (activityInfo.getCreateDate().compareTo(DateUtils.getDate())<0){
            throw  new MyException(MyExceptionEnum.CREATE_DATE_IS_LESS_THAN_TODAY);
        }else if (activityInfo.getExpDate().compareTo(DateUtils.getDate())<0){
            throw  new MyException(MyExceptionEnum.EXP_DATE_IS_LESS_THAN_TODAY);
        }else {
            activityService.saveActivity(activityInfo);
        }
        return ResponseEntity.ok().build();
    }
    @PostMapping("/submitvideo")
    public ResponseEntity<Model> submitvideo(@RequestParam("file") MultipartFile file,Integer activityId,Integer shopId,Model model){
        String videoUrl = uploadService.uploadVideo(file);
        activityService.updateVideoUrl(videoUrl,activityId);
        videoService.addVideoInfo(videoUrl,activityId,shopId);
        return ResponseEntity.ok(model);
    }
    @PostMapping("/updateVideoUrl")
    public ResponseEntity<Void> updateVideoUrl(String videoUrl,Integer activityId){
        activityService.updateVideoUrl(videoUrl,activityId);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/dropActivity")
    public ResponseEntity dropActivity(ActivityInfo activityInfo){
        activityService.updateActivityStatus(activityInfo);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/editActivity")
    public String editActivity(Integer activityId, Model model){
        ActivityInfo activityInfo = activityService.queryActivityById(activityId);
        model.addAttribute("activityInfo",activityInfo);
        List<CardInfo> cardList = cardService.queryCardList();
        model.addAttribute("cardList",cardList);
        return "editActivity";
    }
    @PostMapping("/saveEditActivity")
    public ResponseEntity saveEditActivity(@RequestBody ActivityInfo activityInfo){
        activityService.saveEditActivity(activityInfo);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/catActivity")
    public String catActivity(Integer activityId, Model model){
        ActivityInfo activityInfo = activityService.queryActivityById(activityId);
        ActivityData activityData = userCardService.selectActivityDataById(activityId);
        activityData.setUsers(activityInfo.getUsers());
        model.addAttribute("activityData",activityData);
        return "activityData";
    }

    /**
     * @author: zzh
     * @description: 领取优惠券
     * @date: 2021/4/13 14:23
     * @param openid:
     * @param activityId:
     * @return: {@link ResponseEntity}
     **/
    @RequestMapping("/addUserCard")
    public ResponseEntity addUserCard(String openid,Integer activityId) throws IOException {
        //判断当前活动是否过期
        ActivityInfo activityInfo = activityService.queryActivityById(activityId);
        if (activityInfo.getExpDate().compareTo(DateUtils.getDate())<0)
            throw new MyException(MyExceptionEnum.ACTIVITY_IS_EXP);
        else if (Integer.parseInt(activityInfo.getActivityStatus())==0)
            throw new MyException(MyExceptionEnum.ACTIVITY_IS_EXP);
        UserInfo userInfo = userService.selectByOpenId(openid);
        //抖音上传视频
        DouYinUpload.videoUpload(openid,userInfo.getAccessToken(),activityInfo.getVideoUrl());
        activityService.addUserCard(openid,activityId);
        return ResponseEntity.ok().build();
    }

   /* @RequestMapping("/queryUserCardList")
    public ResponseEntity queryUserCardList(Integer pageNum, Integer pageSize,String openid, Integer activityId){

        PageResult<UserCard> userCardList= userCardService.queryUserCardList(pageNum,pageSize,openid,activityId);

        return ResponseEntity.ok(userCardList);
    }*/

   /* *//*查看我的优惠券*//*
    @RequestMapping("/myCards")
    public String myCards(Integer activityId,String openid,Model model){
        model.addAttribute("activityId",activityId);
        model.addAttribute("openid",openid);
        return "myCards";
    }*/
   @RequestMapping("/myCards")
   public String myCards(Integer activityId,String openid,Model model){
       UserCard userCard = userCardService.queryUserCardList(openid,activityId);

       model.addAttribute("userCard",userCard);
       model.addAttribute("activityId",activityId);
       return "myCards";
   }


}
