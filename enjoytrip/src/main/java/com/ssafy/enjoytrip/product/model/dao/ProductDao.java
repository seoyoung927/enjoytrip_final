package com.ssafy.enjoytrip.product.model.dao;

import com.ssafy.enjoytrip.product.model.dto.BookDto;
import com.ssafy.enjoytrip.product.model.dto.ProductDetailDto;
import com.ssafy.enjoytrip.product.model.dto.ProductDto;
import com.ssafy.enjoytrip.product.model.dto.RequestProductDetailDto;

import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface ProductDao {
    int insert(RequestProductDetailDto productDetailDto) throws SQLException;
    ProductDetailDto search(int productId) throws SQLException;
    List<ProductDto> searchAll() throws SQLException;
    void update(RequestProductDetailDto requestProductDetailDto) throws SQLException;
    void delete(int productId) throws SQLException;
    int checkOwner(BookDto bookDto) throws SQLException;
    List<ProductDto> searchMyProduct(String userId) throws SQLException;
}


