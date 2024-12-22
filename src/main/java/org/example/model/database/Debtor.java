package org.example.model.database;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Debtor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "docNumber", nullable = false)
    private String docNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phone_number;

    public Debtor() {
    }
}
