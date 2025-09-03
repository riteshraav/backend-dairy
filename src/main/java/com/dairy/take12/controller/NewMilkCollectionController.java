package com.dairy.take12.controller;

import com.dairy.take12.model.MilkCollection;
import com.dairy.take12.model.MilkCollectionNotification;
import com.dairy.take12.model.NewMilkCollection;
import com.dairy.take12.service.NewMilkCollectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/api/milk")
public class NewMilkCollectionController {
    private final NewMilkCollectionService service;

    public NewMilkCollectionController(NewMilkCollectionService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<NewMilkCollection> saveCollection(@RequestBody NewMilkCollection milkCollection) {
        return ResponseEntity.ok(service.saveMilkCollection(milkCollection));
    }
    @GetMapping("/notifications/{customerId}")
    public List<NewMilkCollection> getNotifications(@PathVariable String customerId) {
        System.out.println("called get notification");
        return service.findByCustomerIdAndDeliveredFalse(customerId);
    }
    @MessageMapping("/ping")
    @SendToUser("/queue/pong") // send back to the same user
    public String handlePing(Principal principal) {
        return "pong";
    }
    @MessageMapping("/reply") // maps to "/app/reply"
    public void handleReply(@Payload NewMilkCollection collection, Principal principal) {

        service.updateMilkCollectionMarkingDeliveredTrue(collection);

    }

}

