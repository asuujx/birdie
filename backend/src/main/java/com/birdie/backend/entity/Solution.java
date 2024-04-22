package com.birdie.backend.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "solutions")
public class Solution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "course_member_id")
    private Task task;

    @Column(name = "file")
    private String filePath;

    @Column(name = "date_added")
    private Date dateAdded;
}
