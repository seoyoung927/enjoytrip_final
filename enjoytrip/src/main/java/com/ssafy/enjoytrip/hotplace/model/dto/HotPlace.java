package com.ssafy.enjoytrip.hotplace.model.dto;

import com.ssafy.enjoytrip.hotplace.model.dto.RequestInsertHotPlaceDto;
import com.ssafy.enjoytrip.hotplace.model.dto.RequestUpdateHotPlaceDto;
import com.ssafy.enjoytrip.plan.model.dto.ChatResponse.Choice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotPlace {
    private int hid;
    private String userId;
    private String title;
    private String intro;
    private String description;
    private String imagePath;

    public void mapUpdate(RequestUpdateHotPlaceDto requestUpdateHotPlaceDto){
        this.hid = requestUpdateHotPlaceDto.getHid();
        this.userId = requestUpdateHotPlaceDto.getUserId();
        this.title = requestUpdateHotPlaceDto.getTitle();
        this.intro = requestUpdateHotPlaceDto.getIntro();
        this.description = requestUpdateHotPlaceDto.getDescription();
    }
    public void mapInsert(RequestInsertHotPlaceDto requestInsertHotPlaceDto){
        this.userId = requestInsertHotPlaceDto.getUserId();
        this.title = requestInsertHotPlaceDto.getTitle();
        this.intro = requestInsertHotPlaceDto.getIntro();
        this.description = requestInsertHotPlaceDto.getDescription();
    }
}
