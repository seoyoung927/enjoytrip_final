package com.ssafy.enjoytrip.place.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.auth.util.JWTUtil;
import com.ssafy.enjoytrip.place.model.dto.RequestPlaceDto;
import com.ssafy.enjoytrip.product.model.dto.BookDto;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.enjoytrip.place.model.dto.Place;
import com.ssafy.enjoytrip.place.service.PlaceService;


@RestController
@RequestMapping("/place")
@CrossOrigin("*")
public class PlaceController {
	private Logger logger = LoggerFactory.getLogger(PlaceController.class);
	private final PlaceService placeService;
	private final JWTUtil jwtUtil;

	@Autowired
	public PlaceController(PlaceService placeService, JWTUtil jwtUtil) {
		System.out.println("PlaceController......................................");
		this.placeService = placeService;
		this.jwtUtil = jwtUtil;
	}
	
	@GetMapping
	public ResponseEntity<?> searchPlaceByKeyword(@RequestParam String keyword, @RequestParam(required=true,defaultValue="0") int sidoCode, @RequestParam(required=true,defaultValue="0") int contentTypeId, HttpServletRequest request) throws Exception{
		RequestPlaceDto requestPlaceDto = new RequestPlaceDto();
		requestPlaceDto.setKeyword(keyword);
		requestPlaceDto.setSidoCode(sidoCode);
		requestPlaceDto.setContentTypeId(contentTypeId);

		String token = request.getHeader("Authorization");
		List<Place> places;
		System.out.println(token);
		if (token != null && jwtUtil.checkToken(token)) {
			String userId = jwtUtil.getUserId(token);
			places = placeService.searchByKeyword(requestPlaceDto, userId);
		}else
			places = placeService.searchByKeyword(requestPlaceDto);

		if(places!=null && places.size()>0) {
			Map<String, Object> result = new HashMap<>();
			result.put("places", places);
			return new ResponseEntity<Map>(result, HttpStatus.OK);
		}else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}


}
