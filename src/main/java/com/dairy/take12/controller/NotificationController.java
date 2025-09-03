package com.dairy.take12.controller;

import com.dairy.take12.model.MilkCollectionNotification;
import com.dairy.take12.repository.MilkCollectionNotificationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final MilkCollectionNotificationRepository repo;

    public NotificationController(MilkCollectionNotificationRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/{customerId}")
    public List<MilkCollectionNotification> getNotifications(@PathVariable String customerId) {
        return repo.findByCustomerId(customerId);
    }
}

