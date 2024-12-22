package org.example.model.debts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateDebt {
    private String id_debt;
    private String id_debtor;
    private String debt_date;
    private BigDecimal debt;
    private BigDecimal debt_paid;
    private String debt_description;
}
