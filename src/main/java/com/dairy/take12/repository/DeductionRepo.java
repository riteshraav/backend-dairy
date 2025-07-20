package com.dairy.take12.repository;

import com.dairy.take12.model.Deduction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeductionRepo extends MongoRepository<Deduction,String> {
}
