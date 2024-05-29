package com.birdie.backend.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseMemberDetailsResponse {
    private int id;
    private String name;
    private String surname;
    private Integer group;
}
