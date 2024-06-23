package com.birdie.backend.controllers;

import com.birdie.backend.dto.request.GradeRequest;
import com.birdie.backend.dto.request.TaskUpdateRequest;
import com.birdie.backend.exceptions.UnauthorizedException;
import com.birdie.backend.models.CourseMember;
import com.birdie.backend.models.Task;
import com.birdie.backend.models.User;
import com.birdie.backend.models.enummodels.Role;
import com.birdie.backend.repositories.CourseMemberRepository;
import com.birdie.backend.services.FileSystemStorageService;
import com.birdie.backend.services.JwtService;
import com.birdie.backend.services.TaskService;
import com.birdie.backend.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.birdie.backend.config.MessageProvider.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    private final FileSystemStorageService fileSystemStorageService;
    private final JwtService jwtService;
    private final UserService userService;
    private final CourseMemberRepository courseMemberRepository;

    @Autowired
    public TaskController(TaskService taskService,
                          FileSystemStorageService fileSystemStorageService,
                          JwtService jwtService,
                          UserService userService,
                          CourseMemberRepository courseMemberRepository) {
        this.taskService = taskService;
        this.fileSystemStorageService = fileSystemStorageService;
        this.jwtService = jwtService;
        this.userService = userService;
        this.courseMemberRepository = courseMemberRepository;
    }

    @GetMapping("/{taskId}")
    public Task getTask(@PathVariable int taskId) {
        return taskService.getTaskById(taskId);
    }

    @PostMapping("/{taskId}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<String> updateTask(@PathVariable int taskId,
                                             @RequestBody TaskUpdateRequest taskUpdateRequest) {
        try {
            taskService.updateTask(taskId, taskUpdateRequest);
            return new ResponseEntity<>(TASK_UPDATED, HttpStatus.OK);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/{taskId}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<String> deleteTask(@PathVariable int taskId) {
        try {
            taskService.deleteTask(taskId);
            return new ResponseEntity<>(TASK_DELETED, HttpStatus.OK);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/{taskId}/solutions")
    public ResponseEntity<String> addSolution(@RequestHeader("Authorization") String token,
                                              @PathVariable int taskId,
                                              @RequestParam("files") MultipartFile[] files) {
        String jwt = token.replace("Bearer ", "");
        UserDetails userDetails;

        try {
            userDetails = jwtService.loadUserDetailsFromToken(jwt);
        } catch (Exception e) {
            throw new IllegalArgumentException(TOKEN_INVALID);
        }

        User user = userService.getUserByEmail(userDetails.getUsername());

        fileSystemStorageService.store(files, user.getId(), taskId);
        return new ResponseEntity<>(SOLUTION_CREATED, HttpStatus.CREATED);
    }

    @GetMapping("/{taskId}/solutions")
    public Object getSolution(@RequestHeader("Authorization") String token, @PathVariable int taskId) {
        String jwt = token.replace("Bearer ", "");
        UserDetails userDetails;

        try {
            userDetails = jwtService.loadUserDetailsFromToken(jwt);
        } catch (Exception e) {
            throw new IllegalArgumentException(TOKEN_INVALID);
        }

        User user = userService.getUserByEmail(userDetails.getUsername());

        if (user.getRole().equals(Role.ROLE_STUDENT)) {
            CourseMember courseMember = courseMemberRepository.findByTaskAndUser(taskId, user.getId())
                    .orElseThrow(() -> new EntityNotFoundException(COURSE_MEMBER_NOT_FOUND));

            return fileSystemStorageService.getSolutionForStudent(courseMember.getId(), taskId);
        } else if (user.getRole().equals(Role.ROLE_TEACHER)) {
            return fileSystemStorageService.getSolutionForTeacher(taskId);
        } else {
            throw new UnauthorizedException(UNAUTHORIZED);
        }
    }

    @PutMapping("/{taskId}/solutions/{solutionId}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<String> gradeSolution(@PathVariable int taskId,
                              @PathVariable int solutionId,
                              @RequestBody GradeRequest gradeRequest) {
        try {
            fileSystemStorageService.gradeSolution(taskId, solutionId, gradeRequest.getGrade(), gradeRequest.getGradeDescription());
            return new ResponseEntity<>(SOLUTION_UPDATED, HttpStatus.OK);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/{taskId}/solutions/{solutionId}")
    public ResponseEntity<String> deleteSolution(@PathVariable int taskId,
                                                 @PathVariable int solutionId) {
        fileSystemStorageService.deleteSolution(taskId, solutionId);
        return new ResponseEntity<>(SOLUTION_DELETED, HttpStatus.OK);
    }
}
