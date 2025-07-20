package com.dairy.take12.controller;

import com.dairy.take12.model.AdvanceOrganization;
import com.dairy.take12.repository.AdvanceOrganizationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("advanceOrganization")
@CrossOrigin(origins = "*")

public class AdvanceOrganizationController {
    @Autowired
    AdvanceOrganizationRepo advanceOrganizationRepo;
    @PostMapping("/add")
    public void addAdvanceOrganization(@RequestBody AdvanceOrganization advanceOrganization) {
        advanceOrganizationRepo.save(advanceOrganization);
    }
    @GetMapping("/get/{adminId}")
    public ResponseEntity<?> getListForAdmin(@PathVariable String adminId)
    {
       try{
           List<AdvanceOrganization> list = advanceOrganizationRepo.findAllByAdminId(adminId);
           return ResponseEntity.ok(list);
       }
       catch (Exception e)
       {
           System.out.println("exception in get : "+e.getMessage());
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
       }
    }
    @PostMapping("/delete")
    public ResponseEntity<String> deleteAdvanceOrganization(@RequestBody AdvanceOrganization advanceOrganization)
    {
        try{

            advanceOrganizationRepo.deleteByAdminIdAndDate(advanceOrganization.getAdminId(),advanceOrganization.getDate());
            return ResponseEntity.ok("deleted successfully");

        }
        catch (Exception e)
        {
            System.out.println("exception is : "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
