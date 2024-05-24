package com.birdie.backend.dto;

public class UserDetailsResponse {
    private int id;
    private String email;
    private String name;
    private String surname;

    public UserDetailsResponse(int id, String email, String name, String surname) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
    }
}
