package com.ssafy.enjoytrip.place.service;

import java.util.List;

import com.ssafy.enjoytrip.place.model.dto.RequestPlaceDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.place.model.dao.PlaceDao;
import com.ssafy.enjoytrip.place.model.dto.Place;

@Service
public class PlaceServiceImpl implements PlaceService {
	private static final Logger logger = LoggerFactory.getLogger(PlaceServiceImpl.class);
	private PlaceDao placeDao;
	public PlaceServiceImpl(PlaceDao placeDao) {
		this.placeDao = placeDao;
	}
	
	@Override
	public List<Place> searchByKeyword(RequestPlaceDto requestPlaceDto) {
		return placeDao.searchByKeyword(requestPlaceDto);
	}

	@Override
	public List<Place> searchByKeyword(RequestPlaceDto requestPlaceDto, String userId) {
		requestPlaceDto.setUserId(userId);
		return placeDao.searchByKeywordAndPlan(requestPlaceDto);
	}
}