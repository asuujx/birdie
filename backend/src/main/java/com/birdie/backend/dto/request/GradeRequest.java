package com.birdie.backend.dto.request;

public class GradeRequest {
    private int grade;
    private String gradeDescription;

    // Getters and setters
    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getGradeDescription() {
        return gradeDescription;
    }

    public void setGradeDescription(String gradeDescription) {
        this.gradeDescription = gradeDescription;
    }
}
