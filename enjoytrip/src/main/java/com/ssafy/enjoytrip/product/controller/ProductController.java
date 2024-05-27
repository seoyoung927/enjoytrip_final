package com.ssafy.enjoytrip.product.controller;


import com.ssafy.enjoytrip.auth.exception.UnAuthorizedException;
import com.ssafy.enjoytrip.auth.util.JWTUtil;
import com.ssafy.enjoytrip.product.model.dto.BookDto;
import com.ssafy.enjoytrip.product.model.dto.ProductDetailDto;
import com.ssafy.enjoytrip.product.model.dto.ProductDto;
import com.ssafy.enjoytrip.product.model.dto.RequestProductDetailDto;
import com.ssafy.enjoytrip.product.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RequestMapping("/product")
@RestController
public class ProductController {
    private final ProductService productService;
    private final JWTUtil jwtUtil;
	private final String HEADER_AUTH = "Authorization";
	
    public ProductController(ProductService productService, JWTUtil jwtUtil){
        this.productService = productService;
        this.jwtUtil = jwtUtil;
    } 
    
    
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody RequestProductDetailDto requestProductDetailDto, HttpServletRequest request) throws Exception{
    	String userId = (String)request.getAttribute("userId");
    	requestProductDetailDto.setOwnerId(userId);
    	int pid = productService.insert(requestProductDetailDto);
        return new ResponseEntity<>(pid, HttpStatus.OK);
    }
    
    @PostMapping("/file")
    public ResponseEntity<?> insertFile(@RequestParam List<MultipartFile> files, @RequestParam int pid) throws Exception{
    	productService.insertImage(files, pid);
    	return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?> search(@PathVariable("id") int productId, HttpServletRequest request) throws Exception {
    	final String token = request.getHeader(HEADER_AUTH);
    	ProductDetailDto product;
    
    	if (token != null && jwtUtil.checkToken(token)) {
    		String userId = jwtUtil.getUserId(token);
    		BookDto bookDto = new BookDto();
    		bookDto.setPid(productId);
    		bookDto.setUserId(userId);
    		product = productService.search(bookDto);
		}else
			product = productService.search(productId);
    	
        if(product!=null) {
            return new ResponseEntity<ProductDetailDto>(product, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchAll() throws Exception {
    	System.out.println("??/");
        try{
        	List<ProductDto> products = productService.searchAll();
        	if(products!=null && products.size()>0) {
                return new ResponseEntity<>(products, HttpStatus.OK);
            }else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        
    }

    @PatchMapping
    public ResponseEntity<?> update(@RequestBody RequestProductDetailDto requestProductDetailDto, HttpServletRequest request) throws Exception{
        String ownerId = (String)request.getAttribute("userId");
		BookDto bookDto = new BookDto();
		bookDto.setUserId(ownerId);
		bookDto.setPid(requestProductDetailDto.getPid());
    	productService.checkOwner(bookDto);
        productService.update(requestProductDetailDto);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
    @PatchMapping("/file")
    public ResponseEntity<?> updateImage(@RequestParam List<MultipartFile> files, @RequestParam int pid) throws Exception{
    	productService.updateImage(files, pid);
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int productId, HttpServletRequest request) throws Exception{
        String ownerId = (String)request.getAttribute("userId");
		BookDto bookDto = new BookDto();
		bookDto.setUserId(ownerId);
		bookDto.setPid(productId);
		productService.checkOwner(bookDto);
        productService.delete(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/myProduct")
    public ResponseEntity<?> searchMyProduct(HttpServletRequest request) throws Exception{
    	String userId = (String)request.getAttribute("userId");
    	List<ProductDto> products;
    	products = productService.searchMyProduct(userId);
    	if(products == null || products.size() == 0) 
    		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    	return new ResponseEntity<>(products, HttpStatus.OK);
    }
    
    @PostMapping("/book/{id}")
    public ResponseEntity<?> book(@PathVariable("id") int productId, HttpServletRequest request) throws Exception{
    	String userId = (String)request.getAttribute("userId");
		BookDto bookDto = new BookDto();
		bookDto.setUserId(userId);
		bookDto.setPid(productId);
    	productService.book(bookDto);
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PatchMapping("/book")
    public ResponseEntity<?> updateBook(@RequestBody BookDto bookDto) throws Exception{
    	productService.updateBook(bookDto);
    	return new ResponseEntity<>(HttpStatus.OK);
    }
   
    @DeleteMapping("/book/{id}")
    public ResponseEntity<?> cancel(@PathVariable("id") int productId, HttpServletRequest request) throws Exception{
    	String userId = (String)request.getAttribute("userId");
    	BookDto bookDto = new BookDto();
		bookDto.setUserId(userId);
		bookDto.setPid(productId);
    	productService.cancel(bookDto);
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/myBook")
    public ResponseEntity<?> searchMyBook(HttpServletRequest request) throws Exception{
    	String userId = (String)request.getAttribute("userId");
    	List<ProductDto> products;
    	products = productService.searchMyBook(userId);
    	if(products == null || products.size()==0) 
    		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    	else
    		return new ResponseEntity<>(products, HttpStatus.OK);
    }
    
    

}
