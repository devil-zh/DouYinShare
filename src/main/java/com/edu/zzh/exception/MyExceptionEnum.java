package com.edu.zzh.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : zzh
 * @date : 2020/4/17 10:52
 * @describe : 异常枚举类
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MyExceptionEnum {
    INVALID_FILE_TYPE(400, "无效的文件类型"),
    FILE_UPLOAD_ERROR(500,"文件上传失败"),
    USER_NOT_FOUND(500,"用户未找到"),
    SHOP_NOT_FOUND(500,"商家未找到"),
    ACTIVITY_NOT_FOUND(500,"活动未找到"),
    PASSWORD_NOT_RIGHT(500,"密码不对"),
    ACTIVITY_CNT_IS_ZERO(500,"活动次数用完"),
    SHOP_EXP_DATE(500,"商家服务到期"),
    CARD_EXISTS_TODAY(500,"今天领取过了，明天再来"),
    CREATE_DATE_IS_LESS_THAN_TODAY(500,"开始时间不能小于当前日期"),
    EXP_DATE_IS_LESS_THAN_TODAY(500,"截至日期不能小于当前日期"),
    ACTIVITY_IS_EXP(500,"活动已结束"),
    USERNAME_OR_PASSWORD_ERROR(500,"用户名或密码错误"),
    VIDEO_IS_USEED(500,"视频使用中，不能删除"),
    ;
    private int code;
    private String msg;


}
