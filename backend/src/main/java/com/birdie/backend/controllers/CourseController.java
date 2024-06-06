package com.birdie.backend.controllers;

import com.birdie.backend.dto.request.ApproveMemberRequest;
import com.birdie.backend.dto.response.CourseMemberDetailsResponse;
import com.birdie.backend.models.Course;
import com.birdie.backend.models.CourseMember;
import com.birdie.backend.models.Task;
import com.birdie.backend.services.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;
    private final CourseMemberService courseMemberService;
    private final TaskService taskService;

    @Autowired
    public CourseController(CourseService courseService, CourseMemberService courseMemberService, TaskService taskService) {
        this.courseService = courseService;
        this.courseMemberService = courseMemberService;
        this.taskService = taskService;
    }

    @GetMapping
    public List<Course> getAllCourses(@RequestParam("sort") Optional<String> sort) {
        return courseService.getAllCourses(sort);
    }

    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    public Course createCourse(@RequestHeader("Authorization") String token, @RequestBody Course course) {
        return courseService.createCourse(token, course);
    }

    @GetMapping("/{courseId}")
    public Course getCourseById(@PathVariable int courseId) {
        return courseService.getCourseById(courseId);
    }

    @PatchMapping("/{courseId}")
    @PreAuthorize("hasRole('TEACHER')")
    public Course updateCourse(@PathVariable int courseId, @RequestBody Map<String, Object> fields) {
        return courseService.updateCourse(courseId, fields);
    }

    @DeleteMapping("/{courseId}")
    public void deleteCourse(@PathVariable int courseId) {
        courseService.deleteCourse(courseId);
    }

    @PostMapping("/{courseId}/join")
    @PreAuthorize("hasRole('STUDENT') || hasRole('TEACHER')")
    public CourseMember joinCourse(@RequestHeader("Authorization") String token, @PathVariable int courseId) {
        return courseMemberService.addUserToCourse(token, courseId);
    }

    @PostMapping("/{courseId}/approve")
    @PreAuthorize("hasRole('TEACHER')")
    public CourseMember approveMember(@RequestBody ApproveMemberRequest approveMemberRequest) {
        return courseMemberService.approveMember(approveMemberRequest);
    }

    @GetMapping("/{courseId}/members")
    public List<CourseMemberDetailsResponse> getCourseMembers(@PathVariable int courseId) {
        return courseMemberService.getCourseMembers(courseId);
    }

    @DeleteMapping("/{courseId}/members/{memberId}")
    @PreAuthorize("hasRole('TEACHER')")
    public void deleteCourseMember(@PathVariable int courseId, @PathVariable int memberId) {
        courseMemberService.deleteCourseMember(courseId, memberId);
    }

    @GetMapping("/{courseId}/tasks")
    public List<Task> getCourseTasks(@PathVariable int courseId) {
        return taskService.getAllTasksByCourse(courseId);
    }

    @PostMapping("/{courseId}/tasks")
    @PreAuthorize("hasRole('TEACHER')")
    public Task createCourseTask(@PathVariable int courseId, @RequestBody Task task) {
        return taskService.createTask(courseId, task);
    }
}