package com.ssafy.enjoytrip.hotplace.model.dto;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public class ResponseHotPlaceDto {
    private int hid;
    private String userId;
    private String title;
    private String intro;
    private String imagePath;
    private LocalDateTime createdTime;

    public ResponseHotPlaceDto(int hid, String userId, String title, String intro, String imagePath, LocalDateTime createdTime) {
        this.hid = hid;
        this.userId = userId;
        this.title = title;
        this.intro = intro;
        this.imagePath = imagePath;
        this.createdTime = createdTime;
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


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }


}
