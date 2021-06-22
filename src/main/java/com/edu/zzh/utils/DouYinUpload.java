package com.edu.zzh.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Author: zzh
 * @Date: 2021/3/18 17:29
 * @Description:
 */
public class DouYinUpload {

    /*public static void main(String[] args) throws IOException {
        //videoCreate("b916524f-5ea4-4339-b1f2-3e3def05dbc1",
       //         "act.5b4a919626bb794b0fbd6b8261f45e63RoP5Hx9L8TKU9IGctffTq2CC9Oth",
       //         "@9VwQ0OaZC84iaiCrc8orRs771WbgNfiGP5J5qQ3JaVERbvGqhCbheloW5TCUl1xhCjefy2sFJdtQ3diVDnPSOXOn8Sbs8x3EMCt2z9jXK1c=");
       videoUpload("b916524f-5ea4-4339-b1f2-3e3def05dbc1",
               "act.5b4a919626bb794b0fbd6b8261f45e63RoP5Hx9L8TKU9IGctffTq2CC9Oth",
               "http://47.94.156.170:8080/group1/M00/00/00/rBgG_WBVZ-yAHgiNAA0yPryzeNs301.mp4");
        //downloadFile("http://47.94.156.170:8080/group1/M00/00/00/rBgG_WBVZ-yAHgiNAA0yPryzeNs301.mp4");

    }*/
    public static void videoUpload(String openid,String access_token,String videoUrl) throws IOException {
        String baseUrl = "https://open.douyin.com/video/upload/";
        String uploadUrl=baseUrl+"?open_id="+openid+"&access_token="+access_token;
        //下载视频到本地
        downloadFile(videoUrl);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("video","/video.mp4",
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File("/video.mp4")))
                .build();
        Request request = new Request.Builder()
                .url(uploadUrl)
                .method("POST", body)
                .build();
        Response response = client.newCall(request).execute();
        String res = response.body().string();
        System.out.println(res);
        JSONObject jsonResult = JSON.parseObject(res);
        String data = jsonResult.getString("data");
        JSONObject jsonData = JSON.parseObject(data);
        String videoString = jsonData.getString("video");
        JSONObject jsonVideo = JSON.parseObject(videoString);
        String video_id = jsonVideo.getString("video_id");
        System.out.println(video_id);
        //发布
        videoCreate(openid,access_token,video_id);

    }
    public static void videoCreate(String openid,String access_token,String video_id) throws IOException {
        String baseUrl = "https://open.douyin.com/video/create/";
        String uploadUrl=baseUrl+"?open_id="+openid+"&access_token="+access_token;
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        String jsonVideoId="{\"video_id\":\""+video_id+"\"}";
        System.out.println(jsonVideoId);
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, jsonVideoId);
        Request request = new Request.Builder()
                .url(uploadUrl)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
        File video = new File("/video.mp4");
        if (video.exists()){
            video.delete();
            System.out.println("删除成功");
        }else {
            System.out.println("视频不存在");
        }
    }


    /**
     * 获取远程文件
     * @param videoUrl 远程文件路径
     */
    public static void downloadFile(String videoUrl) {
        URL urlfile = null;
        HttpURLConnection httpUrl = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        String localFilePath = "/video.mp4";
        File f = new File(localFilePath);
        try {
            urlfile = new URL(videoUrl);
            httpUrl = (HttpURLConnection) urlfile.openConnection();
            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());
            bos = new BufferedOutputStream(new FileOutputStream(f));
            int len = 2048;
            byte[] b = new byte[len];
            while ((len = bis.read(b)) != -1) {
                bos.write(b, 0, len);
            }
            bos.flush();
            bis.close();
            httpUrl.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
