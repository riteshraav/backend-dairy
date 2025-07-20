package com.dairy.take12.repository;

import com.dairy.take12.model.CustomerBalance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerBalanceRepo extends MongoRepository<CustomerBalance,String> {
}
