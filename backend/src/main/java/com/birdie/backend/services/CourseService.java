package com.birdie.backend.services;

import com.birdie.backend.config.MessageProvider;
import com.birdie.backend.exceptions.EntityDoesNotExistException;
import com.birdie.backend.exceptions.UnauthorizedException;
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

import static com.birdie.backend.config.MessageProvider.*;

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

    public Course getCourse(String token, int courseId) {
        String jwt = token.replace("Bearer ", "");
        UserDetails userDetails;

        try {
            userDetails = jwtService.loadUserDetailsFromToken(jwt);
        } catch (Exception e) {
            throw new IllegalArgumentException(TOKEN_INVALID);
        }

        User user = userService.getUserByEmail(userDetails.getUsername());
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityDoesNotExistException(COURSE_NOT_FOUND));
       Optional<CourseMember> courseMember = courseMemberRepository.findByUserAndCourse(user, course);


        if (courseMember.isPresent()) {
            return courseRepository.findById(courseId)
                    .orElseThrow(() -> new EntityDoesNotExistException(COURSE_NOT_FOUND));
        } else {
            throw new UnauthorizedException(UNAUTHORIZED);
        }
    }

    public void createCourse(String token, Course course) {
        String jwt = token.replace("Bearer ", "");
        Course createdCourse = courseRepository.save(course);
        UserDetails userDetails;

        try {
            userDetails = jwtService.loadUserDetailsFromToken(jwt);
        } catch (Exception e) {
            throw new IllegalArgumentException(MessageProvider.TOKEN_INVALID);
        }

        User user = userService.getUserByEmail(userDetails.getUsername());

        CourseMember courseMember = CourseMember.builder()
                .user(user)
                .course(createdCourse)
                .status(Status.ACTIVE)
                .build();

        courseMemberRepository.save(courseMember);
    }

    public void updateCourse(int courseId, Map<String, Object> fields) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityDoesNotExistException(COURSE_NOT_FOUND));

        fields.forEach((key, value) -> {
            if (key.equals("name")) {
                course.setName((String) value);
            }
        });

        courseRepository.save(course);
    }
    
    public void deleteCourse(int courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityDoesNotExistException(COURSE_NOT_FOUND));

        courseRepository.delete(course);
    }
}
