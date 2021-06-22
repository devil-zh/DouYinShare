package com.edu.zzh.mapper;

import com.edu.zzh.povo.dto.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: zzh
 * @Date: 2021/3/8 15:01
 * @Description:
 */
public interface UserMapper extends Mapper<UserInfo> {


    @Select("<script>" +
            "select user_id userId,user_name userName,user_image userImage,msisdn,salt,regtime,openid,prov,city,sex from user_info " +
            "where 1=1" +
            "<if test=\"userName != '' &amp;&amp; userName!=null \"> and user_name like CONCAT('%',#{userName},'%') </if>" +
            "<if test=\"regtime != '' &amp;&amp; regtime != null\"> and substr(regtime,1,10)=#{regtime} </if>" +
            "<if test=\"sex != '' &amp;&amp; sex!=null \"> and sex=#{sex} </if>" +
            "</script>")
    List<UserInfo> queryUserListByPage(@Param("userName") String userName, @Param("regtime")String regtime,@Param("sex") String sex);

    @Select("select user_id userId,regtime,access_token accessToken from user_info where openid=#{openid}")
    UserInfo selectByOpenId(String openid);

    @Update("update user_info set user_name=#{userName},sex=#{sex},msisdn=#{msisdn},prov=#{prov},city=#{city},salt=#{salt} where user_id=#{userId}")
    void updateById(Integer userId, String userName, String sex, String msisdn, String prov, String city,String salt);
}
