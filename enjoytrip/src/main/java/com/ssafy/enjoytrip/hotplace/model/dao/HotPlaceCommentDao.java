package com.ssafy.enjoytrip.hotplace.model.dao;

import com.ssafy.enjoytrip.hotplace.model.dto.RequestHotPlaceCommentDto;
import com.ssafy.enjoytrip.hotplace.model.dto.ResponseHotPlaceCommentDto;
import com.ssafy.enjoytrip.hotplace.model.dto.ResponseHotPlaceDto;

import java.sql.SQLException;
import java.util.List;

public interface HotPlaceCommentDao {
    void insert(RequestHotPlaceCommentDto requestHotPlaceCommentDto) throws SQLException;
    void update(RequestHotPlaceCommentDto requestHotPlaceCommentDto) throws SQLException;
    void delete(int cid) throws SQLException;
    List<ResponseHotPlaceCommentDto> search(int hid) throws SQLException;
}
