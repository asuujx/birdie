package com.birdie.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @Column(name = "date_updated")
    private Date dateUpdated;

    @Column(name = "grade")
    private int grade;

    @Column(name = "grade_description")
    private String gradeDescription;

    @Column(name = "date_graded")
    private Date dateGraded;
}