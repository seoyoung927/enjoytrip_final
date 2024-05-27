package com.ssafy.enjoytrip.product.service;

import com.ssafy.enjoytrip.product.model.dto.BookDto;
import com.ssafy.enjoytrip.product.model.dto.ProductDetailDto;
import com.ssafy.enjoytrip.product.model.dto.ProductDto;
import com.ssafy.enjoytrip.product.model.dto.RequestProductDetailDto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    int insert(RequestProductDetailDto requestProductDetailDto) throws Exception;
    ProductDetailDto search(int productId) throws Exception;
    ProductDetailDto search(BookDto bookDto) throws Exception;
    List<ProductDto> searchAll() throws Exception;
    int update(RequestProductDetailDto productDetailDto) throws Exception;
    void delete(int productId) throws Exception;
    void checkOwner(BookDto bookDto) throws Exception;
    void book(BookDto bookDto) throws Exception;
    void cancel(BookDto bookDto) throws Exception;
    List<ProductDto> searchMyBook(String userId) throws Exception;
    List<ProductDto> searchMyProduct(String userId) throws Exception;
    void updateBook(BookDto bookDto) throws Exception;
    void insertImage(List<MultipartFile> files, int pid) throws Exception;
    void updateImage(List<MultipartFile> files, int pid) throws Exception;
}
