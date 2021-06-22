package com.edu.zzh.service;

import com.edu.zzh.exception.MyException;
import com.edu.zzh.exception.MyExceptionEnum;
import com.edu.zzh.mapper.UserMapper;
import com.edu.zzh.povo.dto.UserInfo;
import com.edu.zzh.povo.vo.PageResult;
import com.edu.zzh.utils.AES;
import com.edu.zzh.utils.DateUtils;
import com.edu.zzh.utils.DouYinUpload;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author: zzh
 * @Date: 2021/3/8 14:47
 * @Description:
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public PageResult<UserInfo> queryUserListByPage(Integer pageNum, Integer pageSize, String userName, String regtime, String sex) throws Exception {

        //分页
        PageHelper.startPage(pageNum, pageSize);

        //查询
        List<UserInfo> userInfoList = userMapper.queryUserListByPage(userName,regtime,sex);
        for (UserInfo userInfo:userInfoList){
            if (userInfo.getMsisdn()!=null){
                String msisdn_decrypt = AES.decrypt(userInfo.getSalt(),userInfo.getMsisdn());
                userInfo.setMsisdn(msisdn_decrypt);
            }
        }
       /* if (CollectionUtils.isEmpty(userInfoList))
            throw new MyException(MyExceptionEnum.USER_NOT_FOUND);*/
        PageInfo<UserInfo> pageInfo = new PageInfo<>(userInfoList);
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }

    public void deleteUserList(List<UserInfo> userList) {
        for (UserInfo userInfo:userList){
            userMapper.deleteByPrimaryKey(userInfo.getUserId());
        }
    }

    public void deleteUser(UserInfo userInfo) {
        userMapper.deleteByPrimaryKey(userInfo.getUserId());
    }

    public UserInfo queryUserById(Integer userId) throws Exception {
        UserInfo userInfo = userMapper.selectByPrimaryKey(userId);
        String msisdn = AES.decrypt(userInfo.getSalt(),userInfo.getMsisdn());
        userInfo.setMsisdn(msisdn);
        return userInfo;
    }

    public void saveEditUser(UserInfo userInfo) throws Exception {
        Integer userId = userInfo.getUserId();
        String userName = userInfo.getUserName();
        String sex = userInfo.getSex();
        String salt = AES.getRandomString();
        String msisdn_encrypt = AES.encrypt(salt,userInfo.getMsisdn());
        String prov = userInfo.getProv();
        String city = userInfo.getCity();
        userMapper.updateById(userId,userName,sex,msisdn_encrypt,prov,city,salt);
    }

    public void saveUser(UserInfo userInfo) throws Exception {
        UserInfo user = userMapper.selectByOpenId(userInfo.getOpenid());
        if (userInfo.getMsisdn()!=null){
            //随机生成加密字符串
            String salt = AES.getRandomString();
            //加密手机号
            String msisdn_encrypt = AES.encrypt(salt,userInfo.getMsisdn());
            userInfo.setSalt(salt);
            userInfo.setMsisdn(msisdn_encrypt);
        }
        if (user!=null){
            userInfo.setUserId(user.getUserId());
            userMapper.updateByPrimaryKeySelective(userInfo);
        }else {
            userInfo.setRegtime(DateUtils.getDateTime());
            userMapper.insert(userInfo);
        }
    }
    public UserInfo selectByOpenId(String openid){
        return userMapper.selectByOpenId(openid);
    }
}
