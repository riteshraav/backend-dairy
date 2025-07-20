package com.dairy.take12.repository;

import com.dairy.take12.model.Ledger;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LedgerRepo extends MongoRepository<Ledger,String> {


}
