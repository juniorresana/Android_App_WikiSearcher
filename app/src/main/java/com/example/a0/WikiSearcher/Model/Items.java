package com.example.a0.WikiSearcher.Model;

public class Items {
    private String title;
    private String description;

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    private String pageId;

    public Items(String title, String description, String pageId){
        this.title = title;
        this.description = description;
        this.pageId = pageId;
    }
    public Items(String title, String pageId){
        this.title = title;
        this.description = description;
        this.pageId = pageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
