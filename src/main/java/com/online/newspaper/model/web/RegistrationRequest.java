package com.online.newspaper.model.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
}
