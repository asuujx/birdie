package com.birdie.backend.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TaskUpdateRequest {
    private String name;
    private String description;
    private Date dueDate;
}
