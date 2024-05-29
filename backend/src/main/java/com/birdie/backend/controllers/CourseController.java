package com.birdie.backend.controllers;

import com.birdie.backend.dto.request.ApproveMemberRequest;
import com.birdie.backend.dto.response.CourseMemberDetailsResponse;
import com.birdie.backend.models.Course;
import com.birdie.backend.models.CourseMember;
import com.birdie.backend.models.User;
import com.birdie.backend.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseMemberService courseMemberService;

    @Autowired
    private FetchService fetchService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Course> getAllCourses(@RequestParam("sort") Optional <String> sort) {
        Sort sortOrder = sort
                .filter("desc"::equalsIgnoreCase)
                .map(s -> Sort.by(Sort.Order.desc("name")))
                .orElse(Sort.by(Sort.Order.asc("name")));
        return courseService.getAllCourses(sortOrder);
    }

    @PostMapping
    public Course createCourse(@RequestHeader("Authorization") String token, @RequestBody Course course) {
        String jwt = token.replace("Bearer ", "");
        UserDetails userDetails;

        try {
            userDetails = jwtService.loadUserDetailsFromToken(jwt);
        } catch (Exception e) {
            throw new RuntimeException("Invaild token ", e);
        }

        User user = userService.getUserByEmail(userDetails.getUsername());

        return courseService.createCourse(course, user.getId());
    }

    @GetMapping("/{id}")
    public Optional<Course> getCourseById(@PathVariable int id) {
        return courseService.getCourseById(id);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public Course updateCourse(@PathVariable int id, @RequestBody Map<String, Object> fields) {
        return courseService.updateCourse(id, fields);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable int id) {
        courseService.deleteCourse(id);
    }

    @PostMapping("/{id}/join")
    @PreAuthorize("hasRole('STUDENT') || hasRole('TEACHER')")
    public CourseMember joinCourse(@RequestHeader("Authorization") String token, @PathVariable int id) {
        String jwt = token.replace("Bearer ", "");
        UserDetails userDetails;
        Course course = fetchService.getCourseById(id);

        try {
            userDetails = jwtService.loadUserDetailsFromToken(jwt);
        } catch ( Exception e) {
            throw new RuntimeException("Invalid token", e);
        }

        User user = userService.getUserByEmail(userDetails.getUsername());

        return courseMemberService.addUserToCourse(user, course);
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasRole('TEACHER')")
    public CourseMember approveMember(@RequestBody ApproveMemberRequest approveMemberRequest) {
        CourseMember courseMember = courseMemberService.getCourseMemberById(approveMemberRequest.getCourseMemberId());

        return courseMemberService.approveMember(courseMember);
    }

    @GetMapping("/{id}/members")
    public List<CourseMemberDetailsResponse> getCourseMembers(@PathVariable int id) {
        return courseMemberService.getCourseMembers(id);
    }
}
