package com.dairy.take12.repository;

import com.dairy.take12.model.LocalSale;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LocalSaleRepo extends MongoRepository<LocalSale,String> {
    List<LocalSale> findByAdminIdAndDateBetween(String adminId, Date startDate, Date endDate);
}
