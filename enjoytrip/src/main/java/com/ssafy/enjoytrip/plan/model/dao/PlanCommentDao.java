package com.ssafy.enjoytrip.plan.model.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.plan.model.dto.RequestInsertPlanCommentDto;
import com.ssafy.enjoytrip.plan.model.dto.RequestSearchPlanDetailDto;
import com.ssafy.enjoytrip.plan.model.dto.RequestUpdatePlanCommentDto;
import com.ssafy.enjoytrip.plan.model.dto.ResponseCommentDto;

@Mapper
public interface PlanCommentDao {
	List<ResponseCommentDto> searchMyComment(RequestSearchPlanDetailDto requestSearchPlanDetailDto) throws SQLException;
	List<ResponseCommentDto> searchOtherComment(RequestSearchPlanDetailDto requestSearchPlanDetailDto) throws SQLException;
	void insert(RequestInsertPlanCommentDto requestInsertPlanCommentDto) throws SQLException;
	void update(RequestUpdatePlanCommentDto requestUpdatePlanCommentDto) throws SQLException;
	void delete(int cid) throws SQLException;
}
