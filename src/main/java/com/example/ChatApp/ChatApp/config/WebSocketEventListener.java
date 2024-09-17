package com.example.ChatApp.ChatApp.config;

import com.example.ChatApp.ChatApp.service.Message;
import com.example.ChatApp.ChatApp.service.MessageType;
import jakarta.websocket.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageSendingOperations;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor=StompHeaderAccessor.wrap(event.getMessage());
        String username=(String) headerAccessor.getSessionAttributes().get("username");

        if(Objects.nonNull(username)){            // this is give that this particular user left the chat room
            log.info("User disconnected: {}", username);

            messageSendingOperations.convertAndSend("/topic/chat", Message.builder().type(MessageType.LEAVE).sender(username).build());
        }
    }
}
