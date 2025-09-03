package com.dairy.take12.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "milk_collections")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true) // Ignore extra fields if present in JSON
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewMilkCollection {
    @Id
    private String id;

    private String customerId;
    private String adminId;
    private double fat;
    private double snf;
    private int milkType;
    private int time;
    private String date;
    private double rate;
    private double amount;
    private double quantity;
    private boolean delivered = false;
}


