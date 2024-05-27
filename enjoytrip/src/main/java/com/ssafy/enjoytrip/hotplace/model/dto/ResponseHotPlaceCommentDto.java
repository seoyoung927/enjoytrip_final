package com.ssafy.enjoytrip.hotplace.model.dto;

import java.time.LocalDateTime;

public class ResponseHotPlaceCommentDto {
    int cid;
    String userId;
    String content;
    int hid;
    LocalDateTime createdTime;

    public ResponseHotPlaceCommentDto() {
    }

    public ResponseHotPlaceCommentDto(int cid, String userId, String content, int hid, LocalDateTime createdTime) {
        this.cid = cid;
        this.userId = userId;
        this.content = content;
        this.hid = hid;
        this.createdTime = createdTime;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getHid() {
        return hid;
    }

    public void setHid(int hid) {
        this.hid = hid;
    }


    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}
