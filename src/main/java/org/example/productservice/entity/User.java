package org.example.productservice.entity;

import lombok.Data;

@Data
public class User {

    private int id;
    private String email;
    private String password;
    private UserType userType;
}
