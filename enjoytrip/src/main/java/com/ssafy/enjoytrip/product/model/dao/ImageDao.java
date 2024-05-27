package com.ssafy.enjoytrip.product.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.product.model.dto.Image;

@Mapper
public interface ImageDao {
	int insertImage(Image image);
	List<Image> search(int productId) throws Exception;
	void deleteImage(int productId);
}
