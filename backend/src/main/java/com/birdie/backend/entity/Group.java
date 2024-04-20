package com.birdie.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "name")
    private String name;
}
