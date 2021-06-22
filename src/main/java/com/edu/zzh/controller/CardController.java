package com.edu.zzh.controller;

import com.alibaba.fastjson.JSONObject;
import com.edu.zzh.exception.MyException;
import com.edu.zzh.exception.MyExceptionEnum;
import com.edu.zzh.povo.dto.ActivityInfo;
import com.edu.zzh.povo.dto.CardInfo;
import com.edu.zzh.povo.vo.PageResult;
import com.edu.zzh.service.ActivityService;
import com.edu.zzh.service.CardService;
import com.edu.zzh.service.UserCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: zzh
 * @Date: 2021/3/16 15:18
 * @Description:
 */
@Controller
public class CardController {
    @Autowired
    private CardService cardService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private UserCardService userCardService;

    @RequestMapping("/queryCardListByPage")
    public ResponseEntity queryCardListByPage(Integer pageNum, Integer pageSize,String cardType){

        PageResult<CardInfo> cardListByPage= cardService.queryCardListByPage(pageNum,pageSize,cardType);

        return ResponseEntity.ok(cardListByPage);
    }


    @PostMapping("/saveCard")
    public ResponseEntity saveCard(@RequestBody CardInfo cardInfo){
        cardService.saveCard(cardInfo);
        return ResponseEntity.ok().build();
    }



    /*修改*/
    @PostMapping("/saveEditCard")
    public ResponseEntity saveEditCard(@RequestBody CardInfo cardInfo){
        cardService.saveEditCard(cardInfo);
        return ResponseEntity.ok().build();
    }

    /*批量删除*/
    @DeleteMapping("/deleteCardList")
    public ResponseEntity delCardListById(@RequestBody List<CardInfo> cardList){
        cardService.deleteCardList(cardList);
        return ResponseEntity.ok().build();
    }
    /*
     * @author: zzh
     * @description: 后台管理  卡券删除
     * @date: 2021/5/4 22:15
     * @param cardInfo:
     * @return: {@link org.springframework.http.ResponseEntity}
     **/
    @DeleteMapping("/deleteCard")
    public ResponseEntity delCardById(CardInfo cardInfo){
        cardService.deleteCard(cardInfo);
        return ResponseEntity.ok().build();
    }



    /*//使用优惠券
    @RequestMapping("cardUse")
    public ResponseEntity cardUse(@RequestBody JSONObject cardPWD){
        Integer activityId = Integer.parseInt(cardPWD.getString("activityId")) ;
        Integer id = Integer.parseInt(cardPWD.getString("id")) ;
        String activityPwd = cardPWD.getString("activityPwd");
        ActivityInfo activityInfo = activityService.queryActivityById(activityId);
        if (activityInfo.getActivityPwd().equals(activityPwd)){
            userCardService.updateCardStatus(id);
        }else {
            throw new MyException(MyExceptionEnum.PASSWORD_NOT_RIGHT);
        }

        return ResponseEntity.ok().build();
    }*/
    //使用优惠券
    @RequestMapping("cardUse")
    public ResponseEntity cardUse(Integer activityId,Integer cardId,String activityPwd){
        ActivityInfo activityInfo = activityService.queryActivityById(activityId);
        if (activityInfo.getActivityPwd().equals(activityPwd)){
            userCardService.updateCardStatus(cardId);
        }else {
            throw new MyException(MyExceptionEnum.PASSWORD_NOT_RIGHT);
        }

        return ResponseEntity.ok().build();
    }
}
