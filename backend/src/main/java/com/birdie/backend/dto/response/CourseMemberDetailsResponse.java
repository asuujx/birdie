package com.birdie.backend.dto.response;

import lombok.Getter;
import lombok.Setter;

import javax.swing.text.html.Option;
import java.util.Optional;

@Getter
@Setter
public class CourseMemberDetailsResponse {
    private int id;
    private String name;
    private String surname;
    private Integer group;
}
