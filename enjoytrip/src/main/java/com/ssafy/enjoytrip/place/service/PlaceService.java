package com.ssafy.enjoytrip.place.service;

import java.util.List;

import com.ssafy.enjoytrip.place.model.dto.Place;
import com.ssafy.enjoytrip.place.model.dto.RequestPlaceDto;

public interface PlaceService {
	List<Place> searchByKeyword(RequestPlaceDto requestPlaceDto);
	List<Place> searchByKeyword(RequestPlaceDto requestPlaceDto, String userId);

}
