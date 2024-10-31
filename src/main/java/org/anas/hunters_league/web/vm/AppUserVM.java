package org.anas.hunters_league.web.vm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AppUserVM {
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String nationality;
}
