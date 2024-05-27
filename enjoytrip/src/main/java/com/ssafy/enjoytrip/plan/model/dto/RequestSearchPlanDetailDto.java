package com.ssafy.enjoytrip.plan.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestSearchPlanDetailDto {
	int pid;
	int contentId;
	String userId;
}
