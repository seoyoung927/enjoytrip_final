package com.ssafy.enjoytrip.hotplace.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ResponseHotPlaceDetailDto {
    private int hid;
    private String userId;
    private String title;
    private String intro;
    private String description;
    private String imagePath;
    private LocalDateTime createdTime;
    private List<ResponseHotPlaceCommentDto> comments;

    public ResponseHotPlaceDetailDto() {
    }

    public ResponseHotPlaceDetailDto(int hid, String userId, String title, String intro, String description, String imagePath, LocalDateTime createdTime, List<ResponseHotPlaceCommentDto> comments) {
        this.hid = hid;
        this.userId = userId;
        this.title = title;
        this.intro = intro;
        this.description = description;
        this.imagePath = imagePath;
        this.createdTime = createdTime;
        this.comments = comments;
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

    public List<ResponseHotPlaceCommentDto> getComments() {
        return comments;
    }

    public void setComments(List<ResponseHotPlaceCommentDto> comments) {
        this.comments = comments;
    }
}
