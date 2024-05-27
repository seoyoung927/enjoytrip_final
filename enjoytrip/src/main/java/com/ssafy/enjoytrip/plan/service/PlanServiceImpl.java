package com.ssafy.enjoytrip.plan.service;

import java.util.List;

import com.ssafy.enjoytrip.member.exception.DuplicateException;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.hotplace.model.dao.HotPlaceDao;
import com.ssafy.enjoytrip.place.model.dao.PlaceDao;
import com.ssafy.enjoytrip.plan.model.dao.PlanCommentDao;
import com.ssafy.enjoytrip.plan.model.dao.PlanDao;
import com.ssafy.enjoytrip.plan.model.dto.RequestInsertPlanCommentDto;
import com.ssafy.enjoytrip.plan.model.dto.RequestInsertPlanDto;
import com.ssafy.enjoytrip.plan.model.dto.RequestSearchPlanDetailDto;
import com.ssafy.enjoytrip.plan.model.dto.RequestUpdatePlanCommentDto;
import com.ssafy.enjoytrip.plan.model.dto.ResponsePlanDetailDto;
import com.ssafy.enjoytrip.plan.model.dto.ResponsePlanDto;

@Service
public class PlanServiceImpl implements PlanService{
	
	private final PlanDao planDao;
	private final PlanCommentDao planCommentDao;
	public PlanServiceImpl(PlanDao planDao, PlanCommentDao planCommentDao) {
		this.planDao = planDao;
		this.planCommentDao = planCommentDao;
	}
	
	
	@Override
	public List<ResponsePlanDto> searchAll(String userId) throws Exception {
		// TODO Auto-generated method stub
		return planDao.searchAll(userId);
	}

	@Override
	public ResponsePlanDetailDto search(RequestSearchPlanDetailDto requestSearchPlanDetailDto) throws Exception {
		// TODO Auto-generated method stub
		ResponsePlanDetailDto responsePlanDetailDto = planDao.search(requestSearchPlanDetailDto);
		if(responsePlanDetailDto==null)
			return null;
		responsePlanDetailDto.setMyComment(planCommentDao.searchMyComment(requestSearchPlanDetailDto));
		responsePlanDetailDto.setOtherComment(planCommentDao.searchOtherComment(requestSearchPlanDetailDto));
		return responsePlanDetailDto;
	}

	@Override
	public void addPlan(RequestInsertPlanDto requestInsertPlanDto) throws Exception {
		// TODO Auto-generated method stub
		planDao.insert(requestInsertPlanDto);
	}

	@Override
	public void deletePlan(int planId) throws Exception {
		// TODO Auto-generated method stub
		planDao.delete(planId);
	}


	@Override
	public void addComment(RequestInsertPlanCommentDto requestInsertPlanCommentDto) throws Exception {
		// TODO Auto-generated method stub
		planCommentDao.insert(requestInsertPlanCommentDto);
	}


	@Override
	public void updateComment(RequestUpdatePlanCommentDto requestUpdatePlanCommentDto) throws Exception {
		// TODO Auto-generated method stub
		planCommentDao.update(requestUpdatePlanCommentDto);
	}


	@Override
	public void deleteComment(int cid) throws Exception {
		// TODO Auto-generated method stub
		planCommentDao.delete(cid);
	}

	@Override
	public void checkDuplicate(RequestInsertPlanDto requestInsertPlanDto) throws Exception{
		int count = planDao.check(requestInsertPlanDto);
		if(count!=0)
			throw new DuplicateException();
	}
}
