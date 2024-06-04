package com.birdie.backend.services;

import com.birdie.backend.models.Course;
import com.birdie.backend.models.CourseMember;
import com.birdie.backend.models.User;
import com.birdie.backend.models.enummodels.Status;
import com.birdie.backend.repositories.CourseMemberRepository;
import com.birdie.backend.repositories.CourseRepository;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMemberRepository courseMemberRepository;
    private final JwtService jwtService;
    private final UserService userService;

    public CourseService(CourseRepository courseRepository, CourseMemberRepository courseMemberRepository, JwtService jwtService, UserService userService) {
        this.courseRepository = courseRepository;
        this.courseMemberRepository = courseMemberRepository;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public List<Course> getAllCourses(Optional<String> sort) {
        Sort sortOrder = sort
                .filter("desc"::equalsIgnoreCase)
                .map(s -> Sort.by(Sort.Order.desc("name")))
                .orElse(Sort.by(Sort.Order.asc("name")));

        return courseRepository.findAll(sortOrder);
    }

    public Course createCourse(String token, Course course) {
        String jwt = token.replace("Bearer ", "");
        Course createdCourse = courseRepository.save(course);
        UserDetails userDetails;

        try {
            userDetails = jwtService.loadUserDetailsFromToken(jwt);
        } catch (Exception e) {
            throw new RuntimeException("Invalid token ", e);
        }

        User user = userService.getUserByEmail(userDetails.getUsername());

        CourseMember courseMember = CourseMember.builder()
                .user(user)
                .course(createdCourse)
                .status(Status.ACTIVE)
                .build();

        courseMemberRepository.save(courseMember);

        return createdCourse;
    }

    public Optional<Course> getCourseById(int courseId) {
        return courseRepository.findById(courseId);
    }

    public Course updateCourse(int courseId, Map<String, Object> fields) {
        Optional<Course> course = courseRepository.findById(courseId);

        if (course.isPresent()) {
            Course existingCourse = course.get();
            fields.forEach((key, value) -> {
                if (key.equals("name")) {
                    existingCourse.setName((String) value);
                }
            });

            return courseRepository.save(existingCourse);
        }

        return null;
    }
    
    public void deleteCourse(int courseId) {
        courseRepository.deleteById(courseId);
    }
}
