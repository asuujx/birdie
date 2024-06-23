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

    // Course
    public static final String COURSE_CREATED = "Course created";
    public static final String COURSE_UPDATED = "Course updated";
    public static final String COURSE_DELETED = "Course deleted";

    // Course Member
    public static final String COURSE_MEMBER_CREATED = "Course member created";
    public static final String COURSE_MEMBER_UPDATED = "Course member updated";
    public static final String COURSE_MEMBER_DELETED = "Course member deleted";

    // Group
    public static final String COURSE_GROUP_CREATED = "Course group created";
    public static final String COURSE_GROUP_UPDATED = "Course group updated";
    public static final String COURSE_GROUP_DELETED = "Course group deleted";

    // Task
    public static final String TASK_CREATED = "Task created";
    public static final String TASK_UPDATED = "Task updated";
    public static final String TASK_DELETED = "Task deleted";

    // Solution
    public static final String SOLUTION_CREATED = "Solution created";
    public static final String SOLUTION_UPDATED = "Solution updated";
    public static final String SOLUTION_DELETED = "Solution deleted";
}
