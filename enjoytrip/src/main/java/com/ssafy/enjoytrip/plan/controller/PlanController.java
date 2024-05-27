package com.ssafy.enjoytrip.plan.controller;


import com.ssafy.enjoytrip.plan.model.dto.RequestInsertPlanCommentDto;
import com.ssafy.enjoytrip.plan.model.dto.RequestInsertPlanDto;
import com.ssafy.enjoytrip.plan.model.dto.RequestSearchPlanDetailDto;
import com.ssafy.enjoytrip.plan.model.dto.RequestUpdatePlanCommentDto;
import com.ssafy.enjoytrip.plan.model.dto.ResponsePlanDetailDto;
import com.ssafy.enjoytrip.plan.model.dto.ResponsePlanDto;
import com.ssafy.enjoytrip.plan.service.PlanService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequestMapping("/plan")
@RestController
public class PlanController {
    private final PlanService planService;

    public PlanController(PlanService planService){
        this.planService = planService;
    }


    @GetMapping
    public ResponseEntity<?> searchAll(HttpServletRequest request) throws Exception{
        String userId = (String)request.getAttribute("userId");
        List<ResponsePlanDto> myPlan = planService.searchAll(userId);
        return new ResponseEntity<>(myPlan, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody RequestSearchPlanDetailDto requestSearchPlanDetailDto, HttpServletRequest request) throws Exception{
        String userId = (String)request.getAttribute("userId");
        requestSearchPlanDetailDto.setUserId(userId);
        ResponsePlanDetailDto detail = planService.search(requestSearchPlanDetailDto);
        if(detail==null)
        	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
        	return new ResponseEntity<> (detail, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> addPlan(@PathVariable("id") int contentId, HttpServletRequest request) throws Exception{
        String userId = (String)request.getAttribute("userId");

        RequestInsertPlanDto requestInsertPlanDto = new RequestInsertPlanDto();
        requestInsertPlanDto.setContentId(contentId);
        requestInsertPlanDto.setUserId(userId);
        planService.checkDuplicate(requestInsertPlanDto);
        planService.addPlan(requestInsertPlanDto);
        return new ResponseEntity<>(requestInsertPlanDto.getPid(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlan(@PathVariable("id") int planId) throws Exception{
        planService.deletePlan(planId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping("/comment")
    public ResponseEntity<?> insertComment(@RequestBody RequestInsertPlanCommentDto requestInsertPlanCommentDto) throws Exception{
        planService.addComment(requestInsertPlanCommentDto);
    	return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/comment")
    public ResponseEntity<?> updateComment(@RequestBody RequestUpdatePlanCommentDto requestUpdatePlanCommentDto) throws Exception{
    	planService.updateComment(requestUpdatePlanCommentDto);
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") int cid) throws Exception{
    	planService.deleteComment(cid);
    	return new ResponseEntity<>(HttpStatus.OK);
    }

}
