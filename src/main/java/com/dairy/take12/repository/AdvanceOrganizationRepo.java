package com.dairy.take12.repository;

import com.dairy.take12.model.AdvanceOrganization;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AdvanceOrganizationRepo extends MongoRepository<AdvanceOrganization, String> {

    void deleteByAdminIdAndDate(String adminId, Date date );

    List<AdvanceOrganization> findAllByAdminId(String adminId);
}
