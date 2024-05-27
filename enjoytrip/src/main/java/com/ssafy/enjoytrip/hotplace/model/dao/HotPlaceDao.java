package com.ssafy.enjoytrip.hotplace.model.dao;

import com.ssafy.enjoytrip.hotplace.model.dto.ResponseHotPlaceDetailDto;
import com.ssafy.enjoytrip.hotplace.model.dto.ResponseHotPlaceDto;
import com.ssafy.enjoytrip.hotplace.model.dto.HotPlace;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface HotPlaceDao {
    void insert(HotPlace hotplace) throws SQLException;
    List<ResponseHotPlaceDto> searchAll() throws SQLException;
    ResponseHotPlaceDetailDto search(int hid) throws SQLException;
    void update(HotPlace HotPlace) throws SQLException;
    void delete(int hid) throws SQLException;

    String getImagePath(int hid) throws SQLException;
}
