package com.incarta.clearner.models;

public class ContentModel {

    String id,content_title,content_text,content_img,subtitle_id,content_code;

    public ContentModel(String id, String content_title, String content_text, String content_img, String subtitle_id, String content_code) {
        this.id = id;
        this.content_title = content_title;
        this.content_text = content_text;
        this.content_img = content_img;
        this.subtitle_id = subtitle_id;
        this.content_code = content_code;
    }

    public ContentModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent_title() {
        return content_title;
    }

    public void setContent_title(String content_title) {
        this.content_title = content_title;
    }

    public String getContent_text() {
        return content_text;
    }

    public void setContent_text(String content_text) {
        this.content_text = content_text;
    }

    public String getContent_img() {
        return content_img;
    }

    public void setContent_img(String content_img) {
        this.content_img = content_img;
    }

    public String getSubtitle_id() {
        return subtitle_id;
    }

    public void setSubtitle_id(String subtitle_id) {
        this.subtitle_id = subtitle_id;
    }

    public String getContent_code() {
        return content_code;
    }

    public void setContent_code(String content_code) {
        this.content_code = content_code;
    }
}
