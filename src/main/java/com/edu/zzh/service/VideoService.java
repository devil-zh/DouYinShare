package com.edu.zzh.service;

import com.edu.zzh.exception.MyException;
import com.edu.zzh.exception.MyExceptionEnum;
import com.edu.zzh.mapper.ActivityMapper;
import com.edu.zzh.mapper.ShopMapper;
import com.edu.zzh.mapper.VideoMapper;
import com.edu.zzh.povo.dto.ActivityInfo;
import com.edu.zzh.povo.dto.ShopInfo;
import com.edu.zzh.povo.dto.VideoInfo;
import com.edu.zzh.povo.vo.PageResult;
import com.edu.zzh.utils.DateUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zzh
 * @Date: 2021/3/16 15:18
 * @Description:
 */
@Service
public class VideoService {
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private UploadService uploadService;


    public PageResult<VideoInfo> queryVideoListByPage(Integer pageNum, Integer pageSize, String activityName,String shopName,String flag) {
        //分页
        PageHelper.startPage(pageNum, pageSize);
        //查询
        List<VideoInfo> videoInfoList = videoMapper.queryVideoListByPage(activityName,shopName,flag);
        List<ActivityInfo> activityInfoList = activityMapper.selectAll();
        for (VideoInfo videoInfo:videoInfoList){
            videoInfo.setFlag("未使用");
            for (ActivityInfo activityInfo:activityInfoList){
                if (videoInfo.getVideoUrl().equals(activityInfo.getVideoUrl()))
                    videoInfo.setFlag("使用中");
            }
        }
        PageInfo<VideoInfo> pageInfo = new PageInfo<>(videoInfoList);
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }

    public void updateVideoFlag(String videoUrl){
        videoMapper.updateByUrl(videoUrl);
    }



    public void deleteVideoList(List<VideoInfo> videoList) {
        for (VideoInfo videoInfo:videoList){
            if(videoInfo.getFlag().equals("使用中"))
                throw new MyException(MyExceptionEnum.VIDEO_IS_USEED);
            videoMapper.deleteByPrimaryKey(videoInfo.getVideoId());
            uploadService.deleteFile(videoInfo.getVideoUrl());
        }
    }

    public void deleteVideo(VideoInfo videoInfo) {
        if(videoInfo.getFlag().equals("使用中"))
            throw new MyException(MyExceptionEnum.VIDEO_IS_USEED);
        videoMapper.deleteByPrimaryKey(videoInfo.getVideoId());
        uploadService.deleteFile(videoInfo.getVideoUrl());
    }

    public void addVideoInfo(String videoUrl, Integer activityId,Integer shopId) {
        ActivityInfo activityInfo = activityMapper.selectByPrimaryKey(activityId);
        ShopInfo shopInfo = shopMapper.selectByPrimaryKey(shopId);
        VideoInfo videoInfo = new VideoInfo();
        videoInfo.setShopId(shopId);
        videoInfo.setShopName(shopInfo.getShopName());
        videoInfo.setVideoUrl(videoUrl);
        videoInfo.setActivityId(activityId);
        videoInfo.setActivityName(activityInfo.getActivityName());
        videoInfo.setCreateTime(DateUtils.getDateTime());
        videoInfo.setFlag("使用中");
        videoMapper.insertSelective(videoInfo);
    }
}
