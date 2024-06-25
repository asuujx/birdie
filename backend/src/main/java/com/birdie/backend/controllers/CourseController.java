package com.birdie.backend.controllers;

import com.birdie.backend.dto.request.AddGroupRequest;
import com.birdie.backend.dto.response.CourseMemberDetailsResponse;
import com.birdie.backend.exceptions.UnauthorizedException;
import com.birdie.backend.models.*;
import com.birdie.backend.services.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.birdie.backend.config.MessageProvider.*;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;
    private final CourseMemberService courseMemberService;
    private final TaskService taskService;
    private final GroupService groupService;

    @Autowired
    public CourseController(CourseService courseService,
                            CourseMemberService courseMemberService,
                            TaskService taskService,
                            GroupService groupService) {
        this.courseService = courseService;
        this.courseMemberService = courseMemberService;
        this.taskService = taskService;
        this.groupService = groupService;
    }

    @GetMapping
    public List<Course> getAllCourses(@RequestParam("sort") Optional<String> sort) {
        return courseService.getAllCourses(sort);
    }

    @GetMapping("/{courseId}")
    public Course getCourse(@RequestHeader("Authorization") String token, @PathVariable int courseId) {
        return courseService.getCourse(token, courseId);
    }

    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<String> createCourse(@RequestHeader("Authorization") String token,
                                               @RequestBody Course course) {
        try {
            courseService.createCourse(token, course);
            return new ResponseEntity<>(COURSE_CREATED, HttpStatus.CREATED);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
        }
    }

    @PatchMapping("/{courseId}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<String> updateCourse(@PathVariable int courseId,
                                               @RequestBody Map<String, Object> fields) {
        try {
            courseService.updateCourse(courseId, fields);
            return new ResponseEntity<>(COURSE_UPDATED, HttpStatus.OK);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/{courseId}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<String> deleteCourse(@PathVariable int courseId) {
        try {
            courseService.deleteCourse(courseId);
            return new ResponseEntity<>(COURSE_DELETED, HttpStatus.OK);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/{courseId}/join")
    public ResponseEntity<String> joinCourse(@RequestHeader("Authorization") String token,
                                             @PathVariable int courseId) {
        try {
            courseMemberService.addUserToCourse(token, courseId);
            return new ResponseEntity<>(COURSE_MEMBER_CREATED, HttpStatus.CREATED);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{courseId}/members")
    public List<CourseMemberDetailsResponse> getCourseMembers(@PathVariable int courseId) {
        return courseMemberService.getCourseMembers(courseId);
    }

    @PatchMapping("/{courseId}/members/{memberId}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<String> editCourseMember(@PathVariable int courseId,
                                                   @PathVariable int memberId,
                                                   @RequestBody Map<String, Object> fields) {
        try {
            courseMemberService.editCourseMember(courseId, memberId, fields);
            return new ResponseEntity<>(COURSE_MEMBER_UPDATED, HttpStatus.OK);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/{courseId}/members/{memberId}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<String> deleteCourseMember(@PathVariable int courseId,
                                                     @PathVariable int memberId) {
        try {
            courseMemberService.deleteCourseMember(courseId, memberId);
            return new ResponseEntity<>(COURSE_MEMBER_DELETED, HttpStatus.OK);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{courseId}/groups")
    public List<Group> getCourseGroups(@PathVariable int courseId) {
        return groupService.getAllGroups(courseId);
    }

    @PostMapping("/{courseId}/groups")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<String> createCourseGroup(@PathVariable int courseId,
                                   @RequestBody AddGroupRequest request) {
        try {
            groupService.createCourseGroup(courseId, request.getName());
            return new ResponseEntity<>(COURSE_GROUP_CREATED, HttpStatus.CREATED);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/{courseId}/groups/{groupId}")
    public ResponseEntity<String> deleteCourseGroup(@PathVariable int courseId, @PathVariable int groupId) {
        try {
            groupService.deleteGroup(courseId, groupId);
            return new ResponseEntity<>(COURSE_GROUP_DELETED, HttpStatus.OK);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{courseId}/tasks")
    public List<Task> getCourseTasks(@PathVariable int courseId) {
        return taskService.getAllTasksByCourse(courseId);
    }

    @PostMapping("/{courseId}/tasks")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<String> createCourseTask(@PathVariable int courseId, @RequestBody Task task) {
        try {
            taskService.createTask(courseId, task);
            return new ResponseEntity<>(TASK_CREATED, HttpStatus.CREATED);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
        }
    }
}