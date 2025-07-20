package com.dairy.take12.repository;

import com.dairy.take12.model.CattleFeedPurchase;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CattleFeedStockRepo extends MongoRepository<CattleFeedPurchase, String> {

}
