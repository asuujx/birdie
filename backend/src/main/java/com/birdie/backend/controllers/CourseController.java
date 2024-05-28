package com.birdie.backend.controllers;

import com.birdie.backend.dto.request.ApproveMemberRequest;
import com.birdie.backend.dto.response.CourseMemberDetailsResponse;
import com.birdie.backend.models.Course;
import com.birdie.backend.models.CourseMember;
import com.birdie.backend.models.User;
import com.birdie.backend.services.CourseMemberService;
import com.birdie.backend.services.CourseService;
import com.birdie.backend.services.FetchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping
    public List<Course> getAllCourses(@RequestParam("sort") Optional <String> sort) {
        Sort sortOrder = sort
                .filter("desc"::equalsIgnoreCase)
                .map(s -> Sort.by(Sort.Order.desc("name")))
                .orElse(Sort.by(Sort.Order.asc("name")));
        return courseService.getAllCourses(sortOrder);
    }

    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
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
    public CourseMember joinCourse(@RequestParam int userId, @PathVariable int id) {
        User user = fetchService.getUserById(userId);
        Course course = fetchService.getCourseById(id);

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
