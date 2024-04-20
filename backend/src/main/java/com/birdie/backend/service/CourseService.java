package com.birdie.backend.service;

import com.birdie.backend.entity.Course;
import com.birdie.backend.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    // Create a new course
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    // Get all courses
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Get course by ID
    public Optional<Course> getCourseById(int id) {
        return courseRepository.findById(id);
    }

    // Update course
    public Course updateCourse(int id, Course courseData) {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isPresent()) {
            Course existingCourse = course.get();
            existingCourse.setName(courseData.getName());
            return courseRepository.save(existingCourse);
        }

        return null;
    }

    // Delete all courses
    public void deleteAllCourses() {
        courseRepository.deleteAll();
    }

    // Delete course
    public void deleteCourse(int id) {
        courseRepository.deleteById(id);
    }
}
