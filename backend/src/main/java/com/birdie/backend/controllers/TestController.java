package com.birdie.backend.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/anon")
    public String anonEndPoint() {
        return "everyone can see this";
    }

    @GetMapping("/admins")
    @PreAuthorize("hasRole('ADMIN')")
    public String usersEndPoint() {
        return "ONLY admins can see this";
    }

    @GetMapping("/teachers")
    @PreAuthorize("hasRole('TEACHER')")
    public String adminsEndPoint() {
        return "ONLY teachers can see this";
    }

    @GetMapping("/students")
    @PreAuthorize("hasRole('STUDENT')")
    public String studentsEndPoint() {
        return "ONLY students can see this";
    }
}