package com.incarta.clearner.models;

import java.util.List;

public class IndexModel {
    String title;
    List<SubtitleModel> subtitles;

    public IndexModel() {
    }

    public IndexModel(String title, List<SubtitleModel> subtitles) {
        this.title = title;
        this.subtitles = subtitles;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SubtitleModel> getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(List<SubtitleModel> subtitles) {
        this.subtitles = subtitles;
    }
}
