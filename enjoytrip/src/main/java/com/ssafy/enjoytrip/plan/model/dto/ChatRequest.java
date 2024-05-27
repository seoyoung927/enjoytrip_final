package com.ssafy.enjoytrip.plan.model.dto;

import com.ssafy.enjoytrip.hotplace.model.vo.Message;
import com.ssafy.enjoytrip.plan.model.dto.ChatResponse.Choice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequest {

    private String model;
    private List<Message> messages;
    public ChatRequest(String model, String prompt) {
        this.model = model;
        this.messages =  new ArrayList<>();
        this.messages.add(new Message("user", prompt));
    }
    
}
