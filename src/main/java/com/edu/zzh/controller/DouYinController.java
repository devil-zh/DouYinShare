package com.edu.zzh.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.zzh.config.DouYinProperties;
import com.edu.zzh.povo.dto.ActivityInfo;
import com.edu.zzh.povo.dto.SystemInfo;
import com.edu.zzh.povo.dto.UserInfo;
import com.edu.zzh.service.ActivityService;
import com.edu.zzh.service.SystemService;
import com.edu.zzh.service.UserService;
import com.edu.zzh.utils.AES;
import com.edu.zzh.utils.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: zzh
 * @Date: 2021/3/15 17:49
 * @Description:
 */
@Controller
public class DouYinController {
    @Autowired
    private SystemService systemService;
    @Autowired
    private UserService userService;
    @Autowired
    private DouYinProperties douYinProperties;
    @Autowired
    private ActivityService activityService;

    @RequestMapping("code")
    public String getCode(Integer activityId, Model model){
        SystemInfo systemInfo = systemService.selectSystemInfo();
        String requestUrl = douYinProperties.getBaseUrl()+"/platform/oauth/connect?client_key="+systemInfo.getClientKey()+
                "&response_type=code&scope="+douYinProperties.getScope()+"&redirect_uri="+douYinProperties.getRedirect_uri()+
                activityId;
        model.addAttribute("requestUrl",requestUrl);
        return "shareVideo";
    }
    @RequestMapping("/lookVideo")
    public String lookVideo(String code,Integer activityId,Model model){
        System.out.println(code);
        System.out.println(activityId);

        // 定义地址
        SystemInfo systemInfo = systemService.selectSystemInfo();
        String token_url = douYinProperties.getBaseUrl()+"/oauth/access_token/?client_key=" + systemInfo.getClientKey()
                + "&client_secret="+systemInfo.getClientSecret()+"&code="+code+"&grant_type=authorization_code";
        //发送 GET 请求
        try {
            //获取access token
            String result = HttpClient.sendGet(token_url);
            System.out.println(result);
            JSONObject jsonObject = JSON.parseObject(result);
            String data = jsonObject.getString("data");
            JSONObject json = JSON.parseObject(data);
            String accessToken = json.getString("access_token");
            System.out.println(accessToken);
            String openid = json.getString("open_id");
            System.out.println(openid);
            //获取用户信息
            String userInfoUrl = douYinProperties.getBaseUrl()+"/oauth/userinfo/?open_id="+openid+"&access_token="+
                    accessToken;
            result = HttpClient.sendGet(userInfoUrl);
            System.out.println(result);
            jsonObject = JSON.parseObject(result);
            String userData = jsonObject.getString("data");
            System.out.println(userData);
            JSONObject userJson = JSON.parseObject(userData);
            String prov = userJson.getString("province");//省份
            System.out.println("prov："+prov);
            String nickname = userJson.getString("nickname");//昵称
            System.out.println("nickname："+nickname);
            String city = userJson.getString("city");//城市
            System.out.println("city："+city);
            String gender = userJson.getString("gender");//性别
            System.out.println("gender："+gender);
            String avatar = userJson.getString("avatar_larger");//头像
            System.out.println("avatar："+avatar);
            String encrypt_mobile = userJson.getString("encrypt_mobile");//加密手机号
            System.out.println("encrypt_mobile："+encrypt_mobile);
            UserInfo userInfo = new UserInfo();
            userInfo.setProv(prov);
            userInfo.setCity(city);
            userInfo.setUserName(nickname);
            userInfo.setOpenid(openid);
            userInfo.setUserImage(avatar);
            userInfo.setAccessToken(accessToken);
            if(encrypt_mobile!=null){
                String msisdn = AES.getMsisdn(encrypt_mobile,systemInfo.getClientSecret());
                userInfo.setMsisdn(msisdn);
            }
            if (Integer.parseInt(gender)!=0)
                userInfo.setSex(Integer.parseInt(gender)==1 ? "男":"女");
            ActivityInfo activityInfo = activityService.queryActivityById(activityId);
            userService.saveUser(userInfo);
            activityService.usersAdd(activityId);
            model.addAttribute("userInfo",userInfo);
            model.addAttribute("activityInfo",activityInfo);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "mobileVideo";
    }

}
