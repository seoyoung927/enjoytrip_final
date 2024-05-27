package com.ssafy.enjoytrip.hotplace.model.dto;


import org.springframework.web.multipart.MultipartFile;

public class RequestInsertHotPlaceDto {
    private String userId;
    private String title;
    private String intro;
    private String description;

    public RequestInsertHotPlaceDto(){
    }

    public RequestInsertHotPlaceDto(String userId, String title, String intro, String description) {
        this.userId = userId;
        this.intro = intro;
        this.title = title;
        this.description = description;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
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
