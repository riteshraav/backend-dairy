package com.dairy.take12.config;

// WebSocketConfig.java

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Expose raw WebSocket endpoint at /ws
        registry.addEndpoint("/ws")
                .setAllowedOrigins("*"); // allow all origins for testing
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Prefix for topics to which clients can subscribe
        registry.enableSimpleBroker("/topic","/queue") ;

        // Prefix for messages sent from clients to @MessageMapping
        registry.setApplicationDestinationPrefixes("/app");
    }
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setSendTimeLimit(15 * 1000)   // 15 sec max to send
                .setSendBufferSizeLimit(512 * 1024);
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // Optional: intercept to track ping-pong if you want
    }

}

