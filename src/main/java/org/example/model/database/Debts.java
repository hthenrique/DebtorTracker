package org.example.model.database;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class Debts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_debt;

    @Column(name = "idDebtor", nullable = false)
    private Integer idDebtor;

    @Column(name = "debt_date", nullable = false)
    private String debt_date;

    @Column(name = "debt")
    private BigDecimal debt;

    @Column(name = "debt_paid")
    private BigDecimal debt_paid;

    @Column(name = "debt_missing")
    private BigDecimal debt_missing;

    @Column(name = "debt_description")
    private String debt_description;


}
