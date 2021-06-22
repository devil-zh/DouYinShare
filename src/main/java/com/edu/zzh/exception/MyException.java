package com.edu.zzh.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : zzh
 * @date : 2020/4/17 10:51
 * @describe : 自定义异常
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MyException extends RuntimeException{
    private MyExceptionEnum exceptionEnum;
}
