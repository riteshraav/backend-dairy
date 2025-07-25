package com.dairy.take12.repository;

import com.dairy.take12.model.CustomerAdvance;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAdvanceRepo extends MongoRepository<CustomerAdvance, String> {
    List<CustomerAdvance> findAllByAdminId(String adminId);
}
