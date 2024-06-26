package com.birdie.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

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

    @Column(name = "grade")
    private Integer grade;

    @Column(name = "grade_description")
    private String gradeDescription;

    @Column(name = "date_graded")
    private Date dateGraded;

    @OneToMany(mappedBy = "solution", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<File> files;
}