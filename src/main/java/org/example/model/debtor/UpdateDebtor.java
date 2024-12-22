package org.example.model.debtor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.example.model.database.Debts;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateDebtor {
    private String id;
    private String name;
    private String address;
    private String email;
    private String phone_number;
    private ArrayList<Debts> debts;
}
