package com.birdie.backend.config;

public class MessageProvider {
    // Exceptions
    public static final String COURSE_NOT_FOUND = "Course does not exist";
    public static final String COURSE_MEMBER_NOT_FOUND = "Course member does not exist";
    public static final String COURSE_MEMBER_EXIST = "Course member already exist";
    public static final String USER_NOT_FOUND = "User does not exist";
    public static final String TASK_NOT_FOUND = "Task does not exist";
    public static final String SOLUTION_NOT_FOUND = "Solution does not exist";
    public static final String GROUP_NOT_FOUND = "Group does not exist";
    public static final String UNAUTHORIZED = "You are not authorized to perform this action";

    // Validation
    public static final String TOKEN_INVALID = "Invalid token";
    public static final String REFRESH_TOKEN_INVALID = "Expired token";
    public static final String EMAIL_OR_PASSWORD_INVALID = "Invalid email or password";
    public static final String LOCATION_NULL = "Location cannot be null";
    public static final String FILE_NULL = "File cannot be null";
    public static final String FILE_OUTSIDE = "File cannot be outside current directory";
}
