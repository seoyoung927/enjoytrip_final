package com.ssafy.enjoytrip.place.model.dao;

import java.util.List;

import com.ssafy.enjoytrip.place.model.dto.RequestPlaceDto;
import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.place.model.dto.Place;


@Mapper
public interface PlaceDao {
	List<Place> searchByKeyword(RequestPlaceDto requestPlaceDto);
	List<Place> searchByKeywordAndPlan(RequestPlaceDto requestPlaceDto);
}
