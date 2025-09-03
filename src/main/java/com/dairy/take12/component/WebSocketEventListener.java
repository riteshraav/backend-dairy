package com.dairy.take12.component;

import com.dairy.take12.service.NewMilkCollectionService;
import com.dairy.take12.service.UserSessionService;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class WebSocketEventListener {

    private final UserSessionService userSessionService;
    private final NewMilkCollectionService milkService;

    public WebSocketEventListener(UserSessionService userSessionService,
                                  NewMilkCollectionService milkService) {
        this.userSessionService = userSessionService;
        this.milkService = milkService;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        System.out.println("sha is "+sha );
        String customerId = logStompHeaders(sha);
        if(Objects.equals(customerId, ""))
        {
            System.out.println("customer id is empty");
            userSessionService.markOffline(customerId);
            System.out.println("❌ Marked customer " + customerId + " as offline");
            return;
        }

        System.out.println("Customer {} connected with session {}" + customerId);

        userSessionService.markOnline(customerId);
        // Send any pending messages to client
    }


    public String logStompHeaders(StompHeaderAccessor sha) {
        // Wrap message into StompHeaderAccessor

        // Basic STOMP info
        System.out.println("== STOMP Header Details ==");
        System.out.println("Message Type    : " + sha.getMessageType());    // e.g. CONNECT, CONNECT_ACK
        System.out.println("Command         : " + sha.getCommand());        // STOMP command like CONNECT, SUBSCRIBE, etc.
        System.out.println("Session ID      : " + sha.getSessionId());      // WebSocket session id
        System.out.println("Heartbeat       : " + (sha.getHeartbeat() != null ? sha.getHeartbeat()[0] + "," + sha.getHeartbeat()[1] : "null"));

        // Native headers (custom headers sent by client)
        System.out.println("-- Native Headers --");
//        Map<String, java.util.List<String>> nativeHeaders = sha.getNativeHeaders();
//        if (nativeHeaders != null) {
//            for (Map.Entry<String, java.util.List<String>> entry : nativeHeaders.entrySet()) {
//                System.out.println(" " + entry.getKey() + " = " + entry.getValue());
//            }
//        }

        // All headers (including Spring-internal)
        System.out.println("-- All Headers --");
        for (Map.Entry<String, Object> entry : sha.toMessageHeaders().entrySet()) {
            System.out.println(" " + entry.getKey() + " = " + entry.getValue());
            if(Objects.equals(entry.getKey(), "simpConnectMessage"))
            {
                System.out.println("key is dimpconnectmessage");
                if (entry.getValue() instanceof GenericMessage) {
                    GenericMessage<?> connectMessage = (GenericMessage<?>) entry.getValue();

                    // Print the payload
                    System.out.println("Payload: " + connectMessage.getPayload());

                    // Print all headers inside simpConnectMessage
                    for (Map.Entry<String, Object> headerEntry : connectMessage.getHeaders().entrySet()) {
                        System.out.println("  Header Key: " + headerEntry.getKey()
                                + " | Value: " + headerEntry.getValue());
                        if(Objects.equals(headerEntry.getKey(), "nativeHeaders"))
                        {
                            System.out.println("header entry is nativeheaders");
                            @SuppressWarnings("unchecked")
                            Map<String, List<String>> nativeHeaders =
                                    (Map<String, List<String>>) headerEntry.getValue();

                            // Iterate over all native headers
                            for (Map.Entry<String, List<String>> nativeEntry : nativeHeaders.entrySet()) {
                                System.out.println("    Native Header Key: " + nativeEntry.getKey()
                                        + " | Values: " + nativeEntry.getValue());
                                if(Objects.equals(nativeEntry.getKey(), "customerId"))
                                {
                                   return nativeEntry.getValue().get(0);
                                }
                            }
                        }
                    }
                }
            }

        }

        // Session attributes if any
        System.out.println("-- Session Attributes --");
        if (sha.getSessionAttributes() != null) {
            for (Map.Entry<String, Object> entry : sha.getSessionAttributes().entrySet()) {
                System.out.println(" " + entry.getKey() + " = " + entry.getValue());
            }
        }
        return "";
    }


    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        String customerId = logStompHeaders(sha);
        userSessionService.markOffline(customerId);
    }
}
