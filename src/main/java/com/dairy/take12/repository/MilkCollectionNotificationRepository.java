package com.dairy.take12.repository;

import com.dairy.take12.model.MilkCollectionNotification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MilkCollectionNotificationRepository extends MongoRepository<MilkCollectionNotification,String> {
    List<MilkCollectionNotification> findByCustomerId(String customerId);
}
