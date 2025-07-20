package com.dairy.take12.controller;

import com.dairy.take12.model.CustomerBalance;
import com.dairy.take12.model.LocalSale;
import com.dairy.take12.repository.CustomerBalanceRepo;
import com.dairy.take12.repository.LocalSaleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

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
                System.out.println(localSale.getId());
            }
            System.out.println("localsale paytype is "+localSale.getPaymentType());
            localSaleRepo.save(localSale);
            return ResponseEntity.ok(localSale);
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

    @GetMapping("/getAllLocalSaleEntry/{adminId}/{date}")
    public ResponseEntity<?> getLocalSaleForDay(@PathVariable String date, @PathVariable String adminId) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse(date);

            // End of the day (23:59:59)
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Date endDate = calendar.getTime();

            List<LocalSale> localSaleList = localSaleRepo.findByAdminIdAndDateBetween(adminId, startDate, endDate);
            return ResponseEntity.ok(localSaleList);
        } catch (Exception e) {
            System.out.println("exception inget local sale for day "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
