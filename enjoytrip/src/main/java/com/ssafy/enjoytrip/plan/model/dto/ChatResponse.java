package com.ssafy.enjoytrip.plan.model.dto;

import com.ssafy.enjoytrip.hotplace.model.vo.Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {

    private List<Choice> choices;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Choice {

		private int index;
        private Message message;
        
    }

    
}