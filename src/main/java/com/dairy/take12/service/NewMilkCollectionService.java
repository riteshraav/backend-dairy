package com.dairy.take12.service;


import com.dairy.take12.model.MilkCollectionNotification;
import com.dairy.take12.model.NewMilkCollection;
import com.dairy.take12.repository.NewMilkCollectionRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class NewMilkCollectionService {
    private final NewMilkCollectionRepo repository;
    private final UserSessionService userSessionService;
    private final SimpMessagingTemplate messagingTemplate;

    public NewMilkCollectionService(NewMilkCollectionRepo repository, UserSessionService userSessionService,
                                    SimpMessagingTemplate messagingTemplate
                                    ) {
        this.repository = repository;
        this.userSessionService = userSessionService;
        this.messagingTemplate = messagingTemplate;
    }


    public NewMilkCollection saveMilkCollection(NewMilkCollection milk) {
        // 1️⃣ Save to Mongo first
        NewMilkCollection saved = repository.save(milk);

        // 2️⃣ Check if customer is online

            // Customer is online → send immediately via Kafka
            System.out.println("inside save milk collection service");
        messagingTemplate.convertAndSend("/topic/customer/"+saved.getCustomerId(),saved);
        repository.save(saved);



        return saved;
    }
    public void updateMilkCollectionMarkingDeliveredTrue(NewMilkCollection milk) {
        milk.setDelivered(true);
        // 1️⃣ Save to Mongo first
        repository.save(milk);
    }

    // 4️⃣ Call this when a client reconnects
    public List<NewMilkCollection> pushPendingMessages(String customerId) {
        System.out.println("pending messages for customerid " + customerId);
        List<NewMilkCollection> pending = repository.findByCustomerIdAndDeliveredFalse(customerId);
        System.out.println("pending messages fount : "+pending.size());
        for (NewMilkCollection milk : pending) {
            milk.setDelivered(true); // mark as delivered
            repository.save(milk);
        }
        System.out.println("pushed all pending milkcollection to customer");
        return  pending;
    }

    public List<NewMilkCollection> findByCustomerIdAndDeliveredFalse(String customerId) {

        return pushPendingMessages(customerId);
    }
}

