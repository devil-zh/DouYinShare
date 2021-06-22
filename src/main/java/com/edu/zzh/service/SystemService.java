package com.edu.zzh.service;


import com.edu.zzh.mapper.SystemMapper;
import com.edu.zzh.povo.dto.Admin;
import com.edu.zzh.povo.dto.SystemInfo;
import com.edu.zzh.povo.vo.SystemData;
import com.edu.zzh.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: zzh
 * @Date: 2021/3/7 15:34
 * @Description:
 */
@Service
public class SystemService {
    @Autowired
    private SystemMapper systemMapper;

    public SystemInfo selectSystemInfo(){
        return systemMapper.selectSystemInfo();
    }

    public Admin login(String username){
        Admin admin = systemMapper.login(username);
       return admin;
    }

    public void update(String systemName,String clientKey, String clientSecret) {
        systemMapper.update(systemName,clientKey,clientSecret);
    }

    public void updateLogo(String logoUrl) {
        systemMapper.updateLogo(logoUrl);
    }

    public SystemData selectSystemData() {
        String day = DateUtils.getDate();
        SystemData systemData = new SystemData();
        Integer userCnt = systemMapper.selectUserCnt();
        Integer userDayCnt = systemMapper.selectUserDayCnt(day);
        Integer shopCnt = systemMapper.selectShopCnt();
        Integer shopDayCnt =  systemMapper.selectShopDayCnt(day);
        Integer activityCnt = systemMapper.selectActivityCnt();
        Integer activityDayCnt = systemMapper.selectActivityDayCnt(day);
        systemData.setUserCnt(userCnt);
        systemData.setUserDayCnt(userDayCnt);
        systemData.setShopCnt(shopCnt);
        systemData.setShopDayCnt(shopDayCnt);
        systemData.setActivityCnt(activityCnt);
        systemData.setActivityDayCnt(activityDayCnt);
        return systemData;
    }
}
