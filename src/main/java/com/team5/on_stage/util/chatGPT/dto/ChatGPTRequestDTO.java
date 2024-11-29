package com.team5.on_stage.util.chatGPT.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChatGPTRequestDTO {
    private String model;
    private List<Message> messages;

    public ChatGPTRequestDTO(String model, String prompt) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new Message("user", prompt));
    }
}
