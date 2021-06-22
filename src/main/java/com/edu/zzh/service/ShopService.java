package com.edu.zzh.service;

import com.edu.zzh.exception.MyException;
import com.edu.zzh.exception.MyExceptionEnum;
import com.edu.zzh.mapper.ShopMapper;
import com.edu.zzh.povo.dto.ShopInfo;
import com.edu.zzh.povo.dto.UserInfo;
import com.edu.zzh.povo.vo.PageResult;
import com.edu.zzh.utils.AES;
import com.edu.zzh.utils.DateUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author: zzh
 * @Date: 2021/3/13 13:02
 * @Description:
 */
@Service
public class ShopService {
    @Autowired
    private ShopMapper shopMapper;

    public PageResult<ShopInfo> queryShopListByPage(Integer pageNum, Integer pageSize, String shopName, String regtime, String address) throws Exception {

        //分页
        PageHelper.startPage(pageNum, pageSize);

        //查询
        List<ShopInfo> shopInfoList = shopMapper.queryShopListByPage(shopName,regtime,address);
        for (ShopInfo shopInfo:shopInfoList){
            String msisdn_decrypt = AES.decrypt(shopInfo.getSalt(),shopInfo.getMsisdn());
            shopInfo.setMsisdn(msisdn_decrypt);
        }
        /*if (CollectionUtils.isEmpty(shopInfoList))
            throw new MyException(MyExceptionEnum.SHOP_NOT_FOUND);*/
        PageInfo<ShopInfo> pageInfo = new PageInfo<>(shopInfoList);
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }
    public void deleteShopList(List<ShopInfo> shopList) {
        for (ShopInfo shopInfo:shopList){
            shopMapper.deleteByPrimaryKey(shopInfo.getShopId());
        }
    }

    public void deleteShop(ShopInfo shopInfo) {
        shopMapper.deleteByPrimaryKey(shopInfo.getShopId());
    }

    public void saveEditShop(ShopInfo shopInfo) throws Exception {
        String salt = AES.getRandomString();
        String msisdn_encrypt = AES.encrypt(salt,shopInfo.getMsisdn());
        shopInfo.setSalt(salt);
        shopInfo.setMsisdn(msisdn_encrypt);
        shopMapper.updateByPrimaryKeySelective(shopInfo);
    }

    public ShopInfo queryShopById(Integer shopId) throws Exception {
        ShopInfo shopInfo = shopMapper.selectByPrimaryKey(shopId);
        String msisdn = AES.decrypt(shopInfo.getSalt(),shopInfo.getMsisdn());
        shopInfo.setMsisdn(msisdn);
        return shopInfo;
    }

    public void saveShop(ShopInfo shopInfo) throws Exception {
        shopInfo.setRegtime(DateUtils.getDateTime());
        String salt = AES.getRandomString();
        String msisdn_encrypt = AES.encrypt(salt,shopInfo.getMsisdn());
        shopInfo.setSalt(salt);
        shopInfo.setMsisdn(msisdn_encrypt);
        shopMapper.insert(shopInfo);
    }

    public void updateCtivityCnt(Integer shopId,Integer activityCnt) {
        shopMapper.updateActivityCnt(shopId,activityCnt);
    }
}
