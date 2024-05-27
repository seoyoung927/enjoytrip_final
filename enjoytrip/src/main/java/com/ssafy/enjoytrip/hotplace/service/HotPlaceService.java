package com.ssafy.enjoytrip.hotplace.service;

import com.ssafy.enjoytrip.hotplace.model.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper
public interface HotPlaceService {
    List<ResponseHotPlaceDto> searchAll() throws Exception;
    ResponseHotPlaceDetailDto searchDetail(int id) throws Exception;
    void insert(RequestInsertHotPlaceDto requestInsertHotPlaceDto, MultipartFile image) throws Exception;
    void update(RequestUpdateHotPlaceDto requestUpdateHotPlaceDto, MultipartFile image) throws Exception;
    void delete(int id) throws Exception;
    void insertComment(RequestHotPlaceCommentDto requestHotPlaceCommentDto) throws Exception;
    void updateComment(RequestHotPlaceCommentDto requestHotPlaceCommentDto) throws Exception;
    void deleteComment(int hid) throws Exception;
}

