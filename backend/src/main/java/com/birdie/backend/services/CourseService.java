package com.birdie.backend.services;

import com.birdie.backend.models.Course;
import com.birdie.backend.models.CourseMember;
import com.birdie.backend.models.User;
import com.birdie.backend.repositories.CourseMemberRepository;
import com.birdie.backend.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseMemberRepository courseMemberRepository;

    public List<Course> getAllCourses(Sort sort) {
        return courseRepository.findAll(sort);
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Optional<Course> getCourseById(int id) {
        return courseRepository.findById(id);
    }

    public List<Course> getCoursesByUser(User user) {
        return courseMemberRepository.findByUser(user)
                .stream()
                .map(CourseMember::getCourse)
                .collect(Collectors.toList());
    }

    public Course updateCourse(int id, Map<String, Object> fields) {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isPresent()) {
            Course existingCourse = course.get();
            fields.forEach((key, value) -> {
                if (key.equals("name")) {
                    existingCourse.setName((String) value);
                    // add more fields as necessary
                }
            });
            return courseRepository.save(existingCourse);
        }

        return null;
    }
    
    public void deleteCourse(int id) {
        courseRepository.deleteById(id);
    }
}
