package com.birdie.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id", nullable = false)
    private int id;

    @Column(name = "file_path", nullable = false)
    private String file_path;

    @Column(name = "date_added", nullable = false)
    private Date date_added;

    @ManyToOne
    @JoinColumn(name = "solution_id", nullable = false)
    private Solution solution;
}
