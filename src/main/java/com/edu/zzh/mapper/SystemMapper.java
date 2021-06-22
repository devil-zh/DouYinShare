package com.edu.zzh.mapper;



import com.edu.zzh.povo.dto.Admin;
import com.edu.zzh.povo.dto.SystemInfo;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @Author: zzh
 * @Date: 2021/3/7 15:10
 * @Description:
 */
public interface SystemMapper {
    @Select("select system_name systemName,logoUrl,client_key clientKey,client_secret clientSecret from system")
    SystemInfo selectSystemInfo();

    @Update("update system set system_name=#{systemName} ,client_key=#{clientKey},client_secret=#{clientSecret}")
    void update(String systemName,String clientKey,String clientSecret);

    @Update("update system set logoUrl=#{logoUrl}")
    void updateLogo(String logoUrl);

    @Select("select password,salt from admin where username=#{username}")
    Admin login(String username);




    @Select("select count(1) userCnt from user_info")
    Integer selectUserCnt();

    @Select("select count(1) userDayCnt from user_info where substr(regtime,1,10)=#{day}")
    Integer selectUserDayCnt(String day);

    @Select("select count(1) shopCnt from shop_info")
    Integer selectShopCnt();

    @Select("select count(1) shopDayCnt from shop_info where substr(regtime,1,10)=#{day}")
    Integer selectShopDayCnt(String day);

    @Select("select count(1) activityCnt from activity_info")
    Integer selectActivityCnt();

    @Select("select count(1) activityDayCnt from activity_info where substr(create_date,1,10)=#{day}")
    Integer selectActivityDayCnt(String day);
}
