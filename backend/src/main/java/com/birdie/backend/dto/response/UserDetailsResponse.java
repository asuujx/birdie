package com.birdie.backend.dto.response;

import com.birdie.backend.models.enummodels.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsResponse {
    private int id;
    private String email;
    private String name;
    private String surname;
    private Role role;

    public UserDetailsResponse(int id, String email, String name, String surname, Role role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.role =role;
    }
}
