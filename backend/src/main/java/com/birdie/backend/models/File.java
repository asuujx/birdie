package com.birdie.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "original_name")
    private String original_name;

    @Column(name = "date_added", nullable = false)
    private Date date_added;

    @ManyToOne
    @JsonIgnoreProperties("files")
    @JoinColumn(name = "solution_id", nullable = false)
    private Solution solution;
}
