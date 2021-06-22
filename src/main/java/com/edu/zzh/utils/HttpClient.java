package com.edu.zzh.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HttpClient {


    public static String sendGet(String urlParame) throws IOException {
        //创建httpclient实例
        CloseableHttpClient httpClient= HttpClientBuilder.create().build();
        //创建get请求方法实例对象
        HttpGet getMethod=new HttpGet(urlParame);
        //执行get
        CloseableHttpResponse response = httpClient.execute(getMethod);
        HttpEntity responseEntiry = response.getEntity();
        //获取返回数据
        String responseStr="";
        if(responseEntiry!=null){
            responseStr = EntityUtils.toString(responseEntiry, StandardCharsets.UTF_8);
        }
        //释放http连接
        getMethod.releaseConnection();

        return responseStr;
    }



}


