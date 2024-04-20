package com.birdie.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    // Constructors, Getters, Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
