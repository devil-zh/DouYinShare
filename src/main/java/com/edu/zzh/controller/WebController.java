package com.edu.zzh.controller;

import com.edu.zzh.povo.dto.Admin;
import com.edu.zzh.povo.dto.CardInfo;
import com.edu.zzh.povo.dto.SystemInfo;
import com.edu.zzh.povo.vo.SystemData;
import com.edu.zzh.service.CardService;
import com.edu.zzh.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tk.mybatis.spring.annotation.MapperScan;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: zzh
 * @Date: 2021/3/11 21:28
 * @Description:
 */
@Controller
@MapperScan("com.edu.zzh.mapper")//开启扫描mapper
public class WebController {
    @Autowired
    private SystemService systemService;
    @Autowired
    private CardService cardService;

    /*系统首页*/
    @RequestMapping("/")
    public String toLogin(){
        return "login";
    }
    @RequestMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest){
        httpServletRequest.getSession().removeAttribute("admin");
        return "login";
    }
    @RequestMapping("/home")
    public String home(Model model, HttpServletRequest httpServletRequest){
        Admin admin = (Admin) httpServletRequest.getSession().getAttribute("admin");
        if (admin==null){
            return "login";
        }
        SystemInfo systemInfo = systemService.selectSystemInfo();
        model.addAttribute("systemInfo",systemInfo);
        return "home";
    }
    /*=========================================会员===================================================================*/
    @RequestMapping("/userList")
    public String userList(){
        return "userList";
    }

    /*=========================================商家=============================================================*/
    @RequestMapping("/shopList")
    public String shopList(){
        return "shopList";
    }

    @RequestMapping("/addShop")
    public String addShop(){
        return "addShop";
    }

    /*=========================================活动=============================================================*/
    @RequestMapping("/activityList")
    public String activityList(){
        return "activityList";
    }

    @RequestMapping("/addActivity")
    public String addActivity(Model model){
        List<CardInfo> cardList = cardService.queryCardList();
        model.addAttribute("cardList",cardList);
        return "addActivity";
    }

    @RequestMapping("catShopActivityList")
    public String catShopActivityList(Integer shopId,Model model){
        model.addAttribute("shopId",shopId);
        return "shopActivityList";
    }

    /*=========================================卡券============================================================*/
    /*添加卡券页面*/
    @RequestMapping("/addCard")
    public String addCard(){
        return "addCard";
    }


    @RequestMapping("/cardList")
    public String cardList(Model model){
        List<CardInfo> cardList = cardService.queryCardTypeList();
        model.addAttribute("cardList",cardList);
        return "cardList";
    }


    /*跳转页面*/
    @GetMapping("/editCard")
    public String editCard(Integer cardId, Model model){
        CardInfo cardInfo = cardService.queryCardById(cardId);
        model.addAttribute("cardInfo",cardInfo);
        return "editCard";
    }
    /*=========================================视频=======================================================*/
    @RequestMapping("/videoList")
    public String videoList(){
        return "videoList";
    }









}
