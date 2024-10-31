package org.anas.hunters_league.service.dto;

import lombok.*;

import java.util.UUID;


@Getter
@Setter
public class AppUserDTO {

    private UUID id;
    private String username;
    private String role;
    private String firstName;
    private String lastName;
    private String cin;
    private String email;
    private String nationality;
    private String joinDate;
    private String licenseExpirationDate;
    private String password;
}
