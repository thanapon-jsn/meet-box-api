package com.neah.meetbox.customer;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Entity
@Data
class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @NotEmpty
    @Min(2)
    private Integer age;

    @Email(message = "email is invalid")
    private String email;
}
