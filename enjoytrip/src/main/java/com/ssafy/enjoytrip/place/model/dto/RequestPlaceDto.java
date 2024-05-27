package com.ssafy.enjoytrip.place.model.dto;

public class RequestPlaceDto {
    private String keyword;
    private int sidoCode;
    private int contentTypeId;
    private String userId;

    public RequestPlaceDto() {
    }

    public RequestPlaceDto(String keyword, int sidoCode, int contentTypeId, String userId) {
        this.keyword = keyword;
        this.sidoCode = sidoCode;
        this.contentTypeId = contentTypeId;
        this.userId = userId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getSidoCode() {
        return sidoCode;
    }

    public void setSidoCode(int sidoCode) {
        this.sidoCode = sidoCode;
    }

    public int getContentTypeId() {
        return contentTypeId;
    }

    public void setContentTypeId(int contentTypeId) {
        this.contentTypeId = contentTypeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
