package com.ssafy.enjoytrip.plan.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.plan.model.dto.RequestInsertPlanDto;
import com.ssafy.enjoytrip.plan.model.dto.RequestSearchPlanDetailDto;
import com.ssafy.enjoytrip.plan.model.dto.ResponsePlanDetailDto;
import com.ssafy.enjoytrip.plan.model.dto.ResponsePlanDto;

@Mapper
public interface PlanDao {
	List<ResponsePlanDto> searchAll(String userId) throws Exception;
	ResponsePlanDetailDto search(RequestSearchPlanDetailDto requestSearchPlanDetailDto) throws Exception;
	void insert(RequestInsertPlanDto requestInsertPlanDto) throws Exception;
	void delete(int planId) throws Exception;
	int check(RequestInsertPlanDto requestInsertPlanDto) throws Exception;
}
