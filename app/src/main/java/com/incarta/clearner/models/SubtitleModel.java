package com.incarta.clearner.models;

public class SubtitleModel {
    String id;
    String subtitle;
    boolean isCompleted;
    String page1;
    String page2;
    String page3;


    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getPage1() {
        return page1;
    }

    public void setPage1(String page1) {
        this.page1 = page1;
    }

    public String getPage2() {
        return page2;
    }

    public void setPage2(String page2) {
        this.page2 = page2;
    }

    public String getPage3() {
        return page3;
    }

    public void setPage3(String page3) {
        this.page3 = page3;
    }

//    public SubtitleModel(String subtitle, boolean isCompleted) {
//        this.subtitle = subtitle;
//        this.isCompleted = isCompleted;
//    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SubtitleModel(String id, String subtitle, boolean isCompleted, String page1, String page2, String page3) {
        this.id = id;
        this.subtitle = subtitle;
        this.isCompleted = isCompleted;
        this.page1 = page1;
        this.page2 = page2;
        this.page3 = page3;
    }
}
