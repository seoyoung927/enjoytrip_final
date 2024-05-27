package com.ssafy.enjoytrip.hotplace.service;

import com.ssafy.enjoytrip.hotplace.model.dao.HotPlaceCommentDao;
import com.ssafy.enjoytrip.hotplace.model.dao.HotPlaceDao;
import com.ssafy.enjoytrip.hotplace.model.dto.*;
import com.ssafy.enjoytrip.hotplace.model.dto.HotPlace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class HotPlaceServiceImpl implements HotPlaceService{

    private final HotPlaceDao hotPlaceDao;
    private final HotPlaceCommentDao hotPlaceCommentDao;
    private final String uploadDir = "src/main/data/";

    @Autowired
    public HotPlaceServiceImpl(HotPlaceDao hotPlaceDao, HotPlaceCommentDao hotPlaceCommentDao){
        this.hotPlaceDao = hotPlaceDao;
        this.hotPlaceCommentDao = hotPlaceCommentDao;
    }

    @Override
    public void insert(RequestInsertHotPlaceDto requestInsertHotPlaceDto, MultipartFile image) throws Exception{
        HotPlace hotPlace = new HotPlace();
        hotPlace.mapInsert(requestInsertHotPlaceDto);
        hotPlace.setImagePath(saveImage(image));
        hotPlaceDao.insert(hotPlace);
    }

    @Override
    public List<ResponseHotPlaceDto> searchAll() throws Exception{
        return hotPlaceDao.searchAll();
    }

    @Override
    public ResponseHotPlaceDetailDto searchDetail(int hid) throws Exception{
        ResponseHotPlaceDetailDto responseHotPlaceDetailDto = hotPlaceDao.search(hid);
        List<ResponseHotPlaceCommentDto> comments = hotPlaceCommentDao.search(hid);
        responseHotPlaceDetailDto.setComments(comments);
        return responseHotPlaceDetailDto;
    }

    @Override
    public void update(RequestUpdateHotPlaceDto requestUpdateHotPlaceDto, MultipartFile image) throws Exception{
        String imagePath = hotPlaceDao.getImagePath(requestUpdateHotPlaceDto.getHid());
        deleteImage(imagePath);
        HotPlace hotPlace = new HotPlace();
        hotPlace.mapUpdate(requestUpdateHotPlaceDto);
        hotPlace.setImagePath(saveImage(image));
        hotPlaceDao.update(hotPlace);
    }

    @Override
    public void delete(int id) throws Exception{
        String imagePath = hotPlaceDao.getImagePath(id);
        deleteImage(imagePath);
        hotPlaceDao.delete(id);
    }

    @Override
    public void insertComment(RequestHotPlaceCommentDto requestHotPlaceCommentDto) throws Exception {
        hotPlaceCommentDao.insert(requestHotPlaceCommentDto);
    }

    @Override
    public void updateComment(RequestHotPlaceCommentDto requestHotPlaceCommentDto) throws Exception {
        hotPlaceCommentDao.update(requestHotPlaceCommentDto);
    }

    @Override
    public void deleteComment(int hid) throws Exception {
        hotPlaceCommentDao.delete(hid);
    }

    private String saveImage(MultipartFile file) throws IOException {
        // Create directories if they do not exist
        Path uploadPath = Paths.get(uploadDir+"/image/hotplace"+"/");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String randomString = UUID.randomUUID().toString().replaceAll("-", "");

        // Save the file locally
        String fileName = randomString+file.getOriginalFilename();
        System.out.println(fileName);
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        // Save the file path in the database
        return "/image/hotplace/" + fileName;

    }
    private void deleteImage(String imagePath){
        File file = new File(uploadDir+imagePath);
        if(file.exists()){
            file.delete();
        }
    }

}
