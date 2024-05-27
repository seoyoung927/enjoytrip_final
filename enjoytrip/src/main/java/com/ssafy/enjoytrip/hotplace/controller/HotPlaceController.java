package com.ssafy.enjoytrip.hotplace.controller;


import com.ssafy.enjoytrip.hotplace.model.dto.RequestHotPlaceCommentDto;
import com.ssafy.enjoytrip.hotplace.model.dto.RequestInsertHotPlaceDto;
import com.ssafy.enjoytrip.hotplace.model.dto.RequestUpdateHotPlaceDto;
import com.ssafy.enjoytrip.hotplace.service.HotPlaceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin("*")
@RequestMapping("/hotplace")
@RestController
public class HotPlaceController {
    private HotPlaceService hotPlaceService;

    @Autowired
    public HotPlaceController(HotPlaceService hotPlaceService){
        this.hotPlaceService = hotPlaceService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> searchDetail(@PathVariable int id) throws Exception{
        return new ResponseEntity<>(hotPlaceService.searchDetail(id), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<?> searchAll() throws Exception{
        return new ResponseEntity<>(hotPlaceService.searchAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestPart("hotplace") RequestInsertHotPlaceDto requestInsertHotPlaceDto,@RequestPart("image") MultipartFile image,  HttpServletRequest request) throws Exception{
        hotPlaceService.insert(requestInsertHotPlaceDto, image);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<?> update(@RequestPart("hotplace") RequestUpdateHotPlaceDto requestUpdateHotPlaceDto, @RequestPart("image") MultipartFile image) throws Exception{
        hotPlaceService.update(requestUpdateHotPlaceDto, image);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id)throws Exception{
        hotPlaceService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/comment")
    public ResponseEntity<?> insertComment(@RequestBody RequestHotPlaceCommentDto requestHotPlaceCommentDto) throws Exception{
        
    	hotPlaceService.insertComment(requestHotPlaceCommentDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PatchMapping("/comment")
    public ResponseEntity<?> updateComment(@RequestBody RequestHotPlaceCommentDto requestHotPlaceCommentDto) throws Exception{
        hotPlaceService.updateComment(requestHotPlaceCommentDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") int hid) throws Exception{
        hotPlaceService.deleteComment(hid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
