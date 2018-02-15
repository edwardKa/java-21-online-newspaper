package com.online.newspaper.model.entities;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private List<String> roles;
}
