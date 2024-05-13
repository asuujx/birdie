package com.birdie.backend.controllers;


import com.birdie.backend.models.Course;
import com.birdie.backend.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    // Get all courses
    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    // Create a new course
    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
    }

    // Get course by ID
    @GetMapping("/{id}")
    public Optional<Course> getCourseById(@PathVariable int id) {
        return courseService.getCourseById(id);
    }

    // POST /{id}/join TODO: ONLY STRUDENT CAN ACCESS
    // GET /{id}/members TODO: LIST OF COURSE MEMBERS
    // DELETE /{id}/members/{member_id} TODO: DELETE COURSE MEMBER BY TEACHER
    // /{id}/members/{member_id}/approve TODO: APPROVE MEMBER TO COURSE BY TEACHER

    // Update course by ID TODO: ONLY TEACHER CAN ACCESS
    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable int id, @RequestBody Course courseData) {
        return courseService.updateCourse(id, courseData);
    }

    // Delete course by ID TODO: ONLY TEACHER CAN ACCESS
    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable int id) {
        courseService.deleteCourse(id);
    }
}
