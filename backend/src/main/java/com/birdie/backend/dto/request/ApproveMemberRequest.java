package com.birdie.backend.dto.request;

public class ApproveMemberRequest {
    private int courseMemberId;

    public int getCourseMemberId() {
        return courseMemberId;
    }

    public void setCourseMemberId(int courseMemberId) {
        this.courseMemberId = courseMemberId;
    }
}
