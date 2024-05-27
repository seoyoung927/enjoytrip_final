package com.ssafy.enjoytrip.product.service;

import com.ssafy.enjoytrip.auth.exception.UnAuthorizedException;
import com.ssafy.enjoytrip.member.exception.DuplicateException;
import com.ssafy.enjoytrip.product.model.dao.BookDao;
import com.ssafy.enjoytrip.product.model.dao.ImageDao;
import com.ssafy.enjoytrip.product.model.dao.ProductDao;
import com.ssafy.enjoytrip.product.model.dto.BookDto;
import com.ssafy.enjoytrip.product.model.dto.Image;
import com.ssafy.enjoytrip.product.model.dto.ProductDetailDto;
import com.ssafy.enjoytrip.product.model.dto.ProductDto;
import com.ssafy.enjoytrip.product.model.dto.RequestProductDetailDto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductDao productDao;
    private final BookDao bookDao;
    private final ImageDao imageDao;
    private final String uploadDir = "src/main/data/";
    
    @Autowired
    public ProductServiceImpl(ProductDao productDao, BookDao bookDao, ImageDao imageDao) {
		super();	
		this.productDao = productDao;
		this.bookDao = bookDao;
		this.imageDao = imageDao;
    }

	@Override
    public int insert(RequestProductDetailDto requestProductDetailDto) throws Exception {
		productDao.insert(requestProductDetailDto);
		return requestProductDetailDto.getPid();
    }

    @Override
    public ProductDetailDto search(int productId) throws Exception {
        ProductDetailDto productDetailDto = productDao.search(productId);
    	productDetailDto.setImages(imageDao.search(productId));
        return productDetailDto;
    }
	@Override
	public ProductDetailDto search(BookDto bookDto) throws Exception {
		// TODO Auto-generated method stub
		ProductDetailDto productDetailDto = productDao.search(bookDto.getPid()); 
		productDetailDto.setImages(imageDao.search(bookDto.getPid()));
		int count = bookDao.checkBook(bookDto);
		if(count == 0)
			return productDetailDto;
		productDetailDto.setBooked(true);
		String memo = bookDao.getMemo(bookDto);
		productDetailDto.setMemo(memo);
		
		return productDetailDto;
	}
    @Override
    public List<ProductDto> searchAll() throws Exception {
        return productDao.searchAll();
    }

    @Override
    public int update(RequestProductDetailDto requestProductDetailDto) throws Exception {       
        productDao.update(requestProductDetailDto);
        return requestProductDetailDto.getPid();
    }


    @Override
    public void delete(int productId) throws Exception {
		List<Image> images = imageDao.search(productId);
		delete(images);
    	productDao.delete(productId);
    }

    
    
	@Override
	public void checkOwner(BookDto bookDto) throws Exception {
		int count = productDao.checkOwner(bookDto);
		if(count==0)
			throw new UnAuthorizedException();
	}

	@Override
	public void book(BookDto bookDto) throws Exception {
		int count = bookDao.checkBook(bookDto);
		if(count!=0) throw new DuplicateException();
		bookDao.insert(bookDto);
	}

	@Override
	public void cancel(BookDto bookDto) throws Exception {
		bookDao.delete(bookDto);
	}

	@Override
	public List<ProductDto> searchMyBook(String userId) throws Exception {
		// TODO Auto-generated method stub
		return bookDao.searchMyBook(userId);
	}

	@Override
	public List<ProductDto> searchMyProduct(String userId) throws Exception {
		// TODO Auto-generated method stub
		return productDao.searchMyProduct(userId);
	}

	@Override
	public void updateBook(BookDto bookDto) throws Exception {
		// TODO Auto-generated method stub
		bookDao.updateBook(bookDto);
	}
	

	public void saveImage(MultipartFile file, int pid) throws IOException {
        // Create directories if they do not exist
        Path uploadPath = Paths.get(uploadDir+"/image/product"+String.valueOf(pid)+"/");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        // Save the file locally
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        // Save the file path in the database
        Image image = new Image();
        image.setpid(pid);
        image.setImagePath("/image/product"+String.valueOf(pid)+"/" + fileName);
        imageDao.insertImage(image);
    }

	@Override
	public void insertImage(List<MultipartFile> files, int pid) throws Exception {
		// TODO Auto-generated method stub
		for(int i = 0, size = files.size(); i<size; i++) {
			saveImage(files.get(i), pid);
		}
	}
	

    public void updateImage(List<MultipartFile> files, int pid) throws Exception {
		List<Image> images = imageDao.search(pid);
		delete(images);
        for(int i = 0, size = files.size(); i<size; i++) {
			saveImage(files.get(i), pid);
		}
    }
	private void delete(List<Image> images){
		for(Image image : images) {
			File file = new File(uploadDir+image.getImagePath());
			if(file.exists()){
				file.delete();
			}
		}
	}
}
