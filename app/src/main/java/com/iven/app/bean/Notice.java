package com.iven.app.bean;

/**
 * @author Iven
 * @date 2017/1/17 13:08
 * @Description NoticeView使用的实体类
 */

public class Notice {
    private String title;//最前边的带颜色的文案
    private String content;//内容的文案
    private String titleColor;//最前边的颜色的值
    private String contentColor;//内容的颜色
    private String url;//点击跳转的链接

    public Notice(String title, String content, String titleColor, String contentColor, String url) {
        this.title = title;
        this.content = content;
        this.titleColor = titleColor;
        this.contentColor = contentColor;
        this.url = url;
    }

    public Notice() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(String titleColor) {
        this.titleColor = titleColor;
    }

    public String getContentColor() {
        return contentColor;
    }

    public void setContentColor(String contentColor) {
        this.contentColor = contentColor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
