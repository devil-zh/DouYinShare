package com.edu.zzh.controller;

import com.edu.zzh.povo.dto.ActivityInfo;
import com.edu.zzh.povo.dto.ShopInfo;
import com.edu.zzh.povo.dto.VideoInfo;
import com.edu.zzh.povo.vo.PageResult;
import com.edu.zzh.service.ActivityService;
import com.edu.zzh.service.ShopService;
import com.edu.zzh.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author: zzh
 * @Date: 2021/3/16 16:41
 * @Description:
 */
@Controller
public class VideoController {
    @Autowired
    private VideoService videoService;
    @Autowired
    private ActivityService activityService;
    
    @RequestMapping("/queryVideoListByPage")
    public ResponseEntity queryVideoListByPage(Integer pageNum, Integer pageSize, String activityName,String shopName, String flag){

        PageResult<VideoInfo> videoListByPage= videoService.queryVideoListByPage(pageNum,pageSize,activityName,shopName,flag);

        return ResponseEntity.ok(videoListByPage);
    }
    /**
     * @author: zzh
     * @description: 添加活动视频
     * @date: 2021/4/16 13:31
     * @param activityId:
     * @param shopId:
     * @param model:
     * @return: {@link String}
     **/
    @RequestMapping("/addActivityVideo")
    public String addActivityVideo(Integer activityId, Integer shopId, Model model){
        ActivityInfo activityInfo = activityService.queryActivityById(activityId);
        model.addAttribute("videoUrl",activityInfo.getVideoUrl());
        model.addAttribute("activityId",activityId);
        model.addAttribute("shopId",shopId);
        return "addActivityVideo";
    }

    @DeleteMapping("/deleteVideoList")
    public ResponseEntity delVideoListById(@RequestBody List<VideoInfo> videoList){
        videoService.deleteVideoList(videoList);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteVideo")
    public ResponseEntity delVideoById(VideoInfo videoInfo){
        videoService.deleteVideo(videoInfo);
        return ResponseEntity.ok().build();
    }
}
