package com.team5.on_stage.util.chatGPT.service;

import com.team5.on_stage.util.chatGPT.dto.ChatGPTRequestDTO;
import com.team5.on_stage.util.chatGPT.dto.ChatGPTResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ChatGPTService {
    @Value("${openai.model}")
    private String model;

    @Value("${openai.url}")
    private String apiURL;

    private final RestTemplate restTemplate;

    public String sendMessage(String prompt) {
        ChatGPTRequestDTO request = new ChatGPTRequestDTO(model, prompt);
        ChatGPTResponseDTO response = restTemplate.postForObject(apiURL, request, ChatGPTResponseDTO.class);
        return response.getChoices().get(0).getMessage().getContent();
    }
}
