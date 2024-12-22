package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.example.model.database.Debtor;
import org.example.model.database.Debts;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Fetch implements Response{
    private List<Debtor> debtors;
    private List<Debts> debts;
    private Debtor debtor;
    private Debts debt;
}
