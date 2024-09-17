package com.example.ChatApp.ChatApp.service;

import lombok.*;
import org.springframework.stereotype.Controller;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    private MessageType type;
    private String content;
    private String sender;
}
