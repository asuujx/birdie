package com.birdie.backend.models;

import com.birdie.backend.models.enummodels.Status;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "course_members")
public class CourseMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_member_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}