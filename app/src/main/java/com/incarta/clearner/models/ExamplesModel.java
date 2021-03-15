package com.incarta.clearner.models;

public class ExamplesModel {
    String id;
    String title_id;
    String subtitle_id;
    String program_title;
    String code;
    String output;
    String explanation;

    public ExamplesModel(String id, String title_id, String subtitle_id, String program_title, String code, String output, String explanation) {
        this.id = id;
        this.title_id = title_id;
        this.subtitle_id = subtitle_id;
        this.program_title = program_title;
        this.code = code;
        this.output = output;
        this.explanation = explanation;
    }

    public ExamplesModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle_id() {
        return title_id;
    }

    public void setTitle_id(String title_id) {
        this.title_id = title_id;
    }

    public String getSubtitle_id() {
        return subtitle_id;
    }

    public void setSubtitle_id(String subtitle_id) {
        this.subtitle_id = subtitle_id;
    }

    public String getProgram_title() {
        return program_title;
    }

    public void setProgram_title(String program_title) {
        this.program_title = program_title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
