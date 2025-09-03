package com.dairy.take12.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserSessionService {

    private final Set<String> onlineUsers = ConcurrentHashMap.newKeySet();
    private final SimpMessagingTemplate messagingTemplate;

    public UserSessionService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void markOnline(String customerId) {

        System.out.println("marked online : "+customerId);
        onlineUsers.add(customerId);
    }

    public void markOffline(String customerId) {
        System.out.println("marked offline : "+customerId);
        onlineUsers.remove(customerId);
    }

    public boolean isUserOnline(String customerId) {
        return onlineUsers.contains(customerId);
    }

    // Pseudocode, implement actual session tracking
    public void sendPing(String customerId, Runnable onPong) {
        // send ping to specific user
        messagingTemplate.convertAndSendToUser(customerId,
                "/queue/ping", "ping");
        // store onPong callback for when client replies
    }

}

