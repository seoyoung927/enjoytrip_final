package com.ssafy.enjoytrip.product.model.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.product.model.dto.BookDto;
import com.ssafy.enjoytrip.product.model.dto.ProductDto;

@Mapper
public interface BookDao {
	int checkBook(BookDto bookDto) throws SQLException;
	String getMemo(BookDto bookDto) throws SQLException;
	void insert(BookDto bookDto) throws SQLException;
	void delete(BookDto bookDto) throws SQLException;
	List<ProductDto> searchMyBook(String userId) throws SQLException;
	void updateBook(BookDto bookDto) throws SQLException;
}
