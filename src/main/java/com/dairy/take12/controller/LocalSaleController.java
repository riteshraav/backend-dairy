package com.dairy.take12.controller;

import com.dairy.take12.model.CustomerBalance;
import com.dairy.take12.model.LocalSale;
import com.dairy.take12.repository.CustomerBalanceRepo;
import com.dairy.take12.repository.LocalSaleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;

@RequestMapping("/localSale")
@RestController
@CrossOrigin("*")
public class LocalSaleController {
    @Autowired
    LocalSaleRepo localSaleRepo;
    @Autowired
    CustomerBalanceRepo customerBalanceRepo;
    @PostMapping("/add")
    public ResponseEntity<?> addLocalSaleMilk(@RequestBody LocalSale localSale)
    {
        if(localSale.getId() == null) {
            System.out.println("local sale id is null");
            localSale.setId();
        }
        else {
            System.out.println("local sale id is set already");
        }
        try{
            if(Objects.equals(localSale.getPaymentType(), "Credit"))
            {
                String _id = localSale.getAdminId()+"_"+localSale.getCustomerId();
                CustomerBalance customerBalance = customerBalanceRepo.findById(_id).orElse(new CustomerBalance(_id));
                double creditMilk = customerBalance.getBalanceCreditMilk();

                System.out.println(creditMilk);
                customerBalance.setBalanceCattleFeed(localSale.getTotalValue()+creditMilk);
                customerBalanceRepo.save(customerBalance);
                localSaleRepo.save(localSale);
                System.out.println(localSale.getId());
                return ResponseEntity.ok(localSale);
            }
            else {
              return   ResponseEntity.status(HttpStatus.CONFLICT).body("local sale is not credit");
            }

        }
        catch (Exception e)
        {
            System.out.println("exception while adding local sell "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("exception while adding local sell");
        }

    }

    @PostMapping("/delete")
    public ResponseEntity deleteMilkSale(@RequestBody LocalSale localSale)
    {

        try{
            if(Objects.equals(localSale.getPaymentType(), "Credit"))
            {
                String _id = localSale.getAdminId()+"_"+localSale.getCustomerId();
                CustomerBalance customerBalance = customerBalanceRepo.findById(_id).orElse(new CustomerBalance(_id));
                double creditMilk = customerBalance.getBalanceCreditMilk();

                System.out.println(creditMilk);
                customerBalance.setBalanceCattleFeed(creditMilk - localSale.getTotalValue());
                customerBalanceRepo.save(customerBalance);
            }
            System.out.println(localSale.getId());
            localSaleRepo.deleteById(localSale.getId());
            return ResponseEntity.ok("locale sell deleted");
        }
        catch (Exception e)
        {
            System.out.println("exception while deleting local sell "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("exception while deleting local sell ");
        }
    }
}
