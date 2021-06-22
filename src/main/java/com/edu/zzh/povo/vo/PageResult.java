package com.edu.zzh.povo.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    private int code;
    private String msg;
    private Long count;// 总条数
    private List<T> data;// 当前页数据

    public PageResult() {
    }

    public PageResult(Long count, List<T> data) {
        this.count = count;
        this.data = data;
        this.code=0;
        this.msg="";
    }

    public PageResult(Long count, List<T> data, int code, String msg) {
        this.count = count;
        this.data = data;
        this.code = code;
        this.msg = msg;
    }
}