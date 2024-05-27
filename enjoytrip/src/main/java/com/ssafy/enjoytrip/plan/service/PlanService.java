package com.ssafy.enjoytrip.plan.service;

import java.util.List;

import com.ssafy.enjoytrip.plan.model.dto.RequestInsertPlanCommentDto;
import com.ssafy.enjoytrip.plan.model.dto.RequestInsertPlanDto;
import com.ssafy.enjoytrip.plan.model.dto.RequestSearchPlanDetailDto;
import com.ssafy.enjoytrip.plan.model.dto.RequestUpdatePlanCommentDto;
import com.ssafy.enjoytrip.plan.model.dto.ResponsePlanDetailDto;
import com.ssafy.enjoytrip.plan.model.dto.ResponsePlanDto;

public interface PlanService {
		List<ResponsePlanDto> searchAll(String userId) throws Exception;
		ResponsePlanDetailDto search(RequestSearchPlanDetailDto requestSearchPlanDetailDto) throws Exception;
		void addPlan(RequestInsertPlanDto requestInsertPlanDto) throws Exception;
		void deletePlan(int planId) throws Exception;
		void addComment(RequestInsertPlanCommentDto requestInsertPlanCommentDto) throws Exception;
		void updateComment(RequestUpdatePlanCommentDto requestUpdatePlanCommentDto) throws Exception;
		void deleteComment(int cid) throws Exception;
		void checkDuplicate(RequestInsertPlanDto requestInsertPlanDto) throws Exception;
}
