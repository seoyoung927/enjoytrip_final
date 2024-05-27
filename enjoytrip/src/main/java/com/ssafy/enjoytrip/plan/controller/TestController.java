package com.ssafy.enjoytrip.plan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ssafy.enjoytrip.plan.model.dto.ChatRequest;
import com.ssafy.enjoytrip.plan.model.dto.ChatResponse;

@RestController
@RequestMapping("/bot")
public class TestController {
	@Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    @Autowired
    private RestTemplate template;

    @GetMapping("/chat")
    public String chat(@RequestParam(name = "prompt")String prompt){
    	prompt = prompt + " 근처 놀만한 곳 3개만 정리해줘 글자는 200글자 이내로 해줘";
        ChatRequest request = new ChatRequest(model, prompt);
        ChatResponse chatResponse =  template.postForObject(apiURL, request, ChatResponse.class);
        return chatResponse.getChoices().get(0).getMessage().getContent();
    }
}
