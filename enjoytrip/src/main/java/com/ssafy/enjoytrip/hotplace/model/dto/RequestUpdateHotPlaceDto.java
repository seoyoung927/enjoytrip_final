package com.ssafy.enjoytrip.hotplace.model.dto;

import org.springframework.web.multipart.MultipartFile;

public class RequestUpdateHotPlaceDto {
    private int hid;
    private String userId;
    private String title;
    private String intro;
    private String description;

    public RequestUpdateHotPlaceDto() {
    }

    public RequestUpdateHotPlaceDto(int hid, String userId, String title, String intro, String description) {
        this.hid = hid;
        this.userId = userId;
        this.title = title;
        this.intro = intro;
        this.description = description;
    }

    public int getHid() {
        return hid;
    }

    public void setHid(int hid) {
        this.hid = hid;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
