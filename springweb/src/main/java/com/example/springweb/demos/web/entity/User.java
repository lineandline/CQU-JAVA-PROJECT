package com.example.springweb.demos.web.entity;

import lombok.Data;

@Data
public class User {
    private String username;
    private String password;
    private Integer role;
}
