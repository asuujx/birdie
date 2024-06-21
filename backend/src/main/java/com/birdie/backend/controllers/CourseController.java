package com.birdie.backend.controllers;

import com.birdie.backend.dto.response.CourseMemberDetailsResponse;
import com.birdie.backend.models.Course;
import com.birdie.backend.models.CourseMember;
import com.birdie.backend.models.Group;
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
    private final GroupService groupService;

    @Autowired
    public CourseController(CourseService courseService, CourseMemberService courseMemberService, TaskService taskService, GroupService groupService) {
        this.courseService = courseService;
        this.courseMemberService = courseMemberService;
        this.taskService = taskService;
        this.groupService = groupService;
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

    @GetMapping("/{courseId}/members")
    public List<CourseMemberDetailsResponse> getCourseMembers(@PathVariable int courseId) {
        return courseMemberService.getCourseMembers(courseId);
    }

    @PatchMapping("/{courseId}/members/{memberId}")
    @PreAuthorize("hasRole('TEACHER')")
    public CourseMember editCourseMember(@PathVariable int courseId, @PathVariable int memberId, @RequestBody Map<String, Object> fields) {
        return courseMemberService.editCourseMember(courseId, memberId, fields);
    }

    @DeleteMapping("/{courseId}/members/{memberId}")
    @PreAuthorize("hasRole('TEACHER')")
    public void deleteCourseMember(@PathVariable int courseId, @PathVariable int memberId) {
        courseMemberService.deleteCourseMember(courseId, memberId);
    }

    @GetMapping("/{courseId}/groups")
    public List<Group> getCourseGroups(@PathVariable int courseId) {
        return groupService.getAllGroups(courseId);
    }

    @PostMapping("/{courseId}/groups")
    public Group addCourseGroup(@PathVariable int courseId, @RequestBody String name) {
        return groupService.addCourseGroup(courseId, name);
    }

    @DeleteMapping("/{courseId}/groups/{groupId}")
    public void deleteCourseGroup(@PathVariable int courseId, @PathVariable int groupId) {
        groupService.deleteGroup(courseId, groupId);
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