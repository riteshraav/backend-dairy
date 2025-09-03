package com.dairy.take12.repository;

// MilkCollectionRepository.java

import com.dairy.take12.model.NewMilkCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NewMilkCollectionRepo extends MongoRepository<NewMilkCollection, String> {
    List<NewMilkCollection> findByCustomerIdAndDeliveredFalse(String customerId);
}
