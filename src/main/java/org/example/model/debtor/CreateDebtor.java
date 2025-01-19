package org.example.model.debtor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateDebtor {
    @NotEmpty
    private String name;
    @NotEmpty
    private String doc_number;
    private String address;
    @Email
    private String email;
    private String phone_number;
}
