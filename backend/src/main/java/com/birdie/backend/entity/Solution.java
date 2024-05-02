package com.birdie.backend.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "solutions")
public class Solution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solution_id", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "course_member_id", nullable = false)
    private CourseMember courseMember;

    @OneToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Column(name = "date_added", nullable = false)
    private Date dateAdded;
}