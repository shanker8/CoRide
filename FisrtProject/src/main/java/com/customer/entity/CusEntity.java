package com.customer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CusEntity {

    private String firstName;
    private String lastName;
    private String city;
    private String mail;
    @Id
    private Long mobileNumber;
    private String address;
    private Long postalCode;
    private String password;

}
