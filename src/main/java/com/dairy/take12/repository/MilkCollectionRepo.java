package com.dairy.take12.repository;

import com.dairy.take12.model.MilkCollection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MilkCollectionRepo extends MongoRepository<MilkCollection, String> {


}
