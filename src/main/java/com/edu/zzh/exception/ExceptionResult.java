package com.edu.zzh.exception;

import lombok.Data;

/**
 * @author : zzh
 * @date : 2020/4/17 11:01
 * @describe : 返回异常封装对象
 */
@Data
public class ExceptionResult {
    private int code;
    private String message;
    private Long timestamp;

    public ExceptionResult(MyExceptionEnum em) {
        this.code = em.getCode();
        this.message = em.getMsg();
        this.timestamp = System.currentTimeMillis();
    }
}
