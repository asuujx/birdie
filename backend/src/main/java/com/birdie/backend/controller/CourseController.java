package com.birdie.backend.controller;


import com.birdie.backend.entity.Course;
import com.birdie.backend.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    // Create a new course
    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
    }

    // Get all courses
    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    // Get course by ID
    @GetMapping("/{id}")
    public Optional<Course> getCourseById(@PathVariable int id) {
        return courseService.getCourseById(id);
    }

    // Update course by ID
    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable int id, @RequestBody Course courseData) {
        return courseService.updateCourse(id, courseData);
    }

    // Delete all courses
    @DeleteMapping
    public String deleteAllCourses() {
        courseService.deleteAllCourses();
        return "All courses have been deleted successfully.";
    }

    // Delete course by ID
    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable int id) {
        courseService.deleteCourse(id);
    }
}
