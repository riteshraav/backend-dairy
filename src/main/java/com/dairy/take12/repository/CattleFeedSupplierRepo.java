package com.dairy.take12.repository;

import com.dairy.take12.model.CattleFeedSupplier;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface CattleFeedSupplierRepo extends MongoRepository<CattleFeedSupplier,String> {

    List<CattleFeedSupplier> findAllByAdminId(String adminId);
}
