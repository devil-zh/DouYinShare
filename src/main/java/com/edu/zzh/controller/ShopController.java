package com.edu.zzh.controller;

import com.edu.zzh.povo.dto.ActivityInfo;
import com.edu.zzh.povo.dto.ShopInfo;
import com.edu.zzh.povo.dto.UserInfo;
import com.edu.zzh.povo.vo.PageResult;
import com.edu.zzh.service.ActivityService;
import com.edu.zzh.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: zzh
 * @Date: 2021/3/13 12:41
 * @Description:
 */
@Controller
public class ShopController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private ActivityService activityService;

    @RequestMapping("/queryShopListByPage")
    public ResponseEntity queryShopListByPage(Integer pageNum, Integer pageSize, String shopName, String regtime, String address) throws Exception {

        PageResult<ShopInfo> shopListByPage= shopService.queryShopListByPage(pageNum,pageSize,shopName,regtime,address);

        return ResponseEntity.ok(shopListByPage);
    }
    @DeleteMapping("/deleteShopList")
    public ResponseEntity delShopListById(@RequestBody List<ShopInfo> shopList){
        shopService.deleteShopList(shopList);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/deleteShop")
    public ResponseEntity delShopById(ShopInfo shopInfo){
        shopService.deleteShop(shopInfo);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/editShop")
    public String editShop(Integer shopId, Model model) throws Exception {
        ShopInfo shopInfo = shopService.queryShopById(shopId);
        model.addAttribute("shopInfo",shopInfo);
        return "editShop";
    }
    @PostMapping("/saveEditShop")
    public ResponseEntity saveEditShop(@RequestBody ShopInfo shopInfo) throws Exception {
        shopService.saveEditShop(shopInfo);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/saveShop")
    public ResponseEntity saveShop(@RequestBody ShopInfo shopInfo) throws Exception {
        shopService.saveShop(shopInfo);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/queryShopListById")
    public ResponseEntity queryShopListById(Integer pageNum, Integer pageSize, String activityStatus, String activityName,Integer shopId){

        PageResult<ActivityInfo> activityListByPage= activityService.queryShopListById(pageNum,pageSize,activityStatus,activityName,shopId);

        return ResponseEntity.ok(activityListByPage);
    }
}
