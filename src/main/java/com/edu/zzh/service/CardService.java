package com.edu.zzh.service;

import com.edu.zzh.exception.MyException;
import com.edu.zzh.exception.MyExceptionEnum;
import com.edu.zzh.mapper.CardMapper;
import com.edu.zzh.povo.dto.CardInfo;
import com.edu.zzh.povo.dto.ShopInfo;
import com.edu.zzh.povo.vo.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author: zzh
 * @Date: 2021/3/16 15:18
 * @Description:
 */
@Service
public class CardService {
    @Autowired
    private CardMapper cardMapper;

    public void saveCard(CardInfo cardInfo) {
        cardMapper.insert(cardInfo);
    }

    public PageResult<CardInfo> queryCardListByPage(Integer pageNum, Integer pageSize,String cardType) {
        //分页
        PageHelper.startPage(pageNum, pageSize);
        //查询
        List<CardInfo> cardInfoList = cardMapper.queryCardListByPage(cardType);
        if (CollectionUtils.isEmpty(cardInfoList))
            throw new MyException(MyExceptionEnum.SHOP_NOT_FOUND);
        PageInfo<CardInfo> pageInfo = new PageInfo<>(cardInfoList);
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }
    public List<CardInfo> queryCardList(){
        return cardMapper.queryCardList();
    }
    public List<CardInfo> queryCardTypeList(){
        return cardMapper.queryCardTypeList();
    }

    public void saveEditCard(CardInfo cardInfo) {
        cardMapper.updateByPrimaryKey(cardInfo);
    }

    public CardInfo queryCardById(Integer cardId) {
        return cardMapper.selectByPrimaryKey(cardId);
    }

    public void deleteCardList(List<CardInfo> cardList) {
        for (CardInfo cardInfo:cardList){
            cardMapper.deleteByPrimaryKey(cardInfo.getCardId());
        }
    }

    public void deleteCard(CardInfo cardInfo) {
        cardMapper.deleteByPrimaryKey(cardInfo.getCardId());
    }
}
