package com.zongfi.zrecycleview.pojo;

/**
 * Created by ZHZEPHI on 2015/10/10.
 */
public class News {

    private String title; //新闻标题
    private String content; //新闻内容
    private String imgPath; //新闻图片
    private String time; //发布时间
    private String sourceUrl; //详细页地址

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
}
