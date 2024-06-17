package com.birdie.backend.controllers;

import com.birdie.backend.dto.request.TaskUpdateRequest;
import com.birdie.backend.handlers.StorageException;
import com.birdie.backend.models.CourseMember;
import com.birdie.backend.models.Task;
import com.birdie.backend.models.User;
import com.birdie.backend.models.enummodels.Role;
import com.birdie.backend.repositories.CourseMemberRepository;
import com.birdie.backend.services.FileSystemStorageService;
import com.birdie.backend.services.JwtService;
import com.birdie.backend.services.TaskService;
import com.birdie.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    private final FileSystemStorageService fileSystemStorageService;
    private final JwtService jwtService;
    private final UserService userService;
    private final CourseMemberRepository courseMemberRepository;


    @Autowired
    public TaskController(TaskService taskService, FileSystemStorageService fileSystemStorageService, JwtService jwtService, UserService userService, CourseMemberRepository courseMemberRepository) {
        this.taskService = taskService;
        this.fileSystemStorageService = fileSystemStorageService;
        this.jwtService = jwtService;
        this.userService = userService;
        this.courseMemberRepository = courseMemberRepository;
    }

    @GetMapping("/{taskId}")
    public Task getTaskById(@PathVariable int taskId) {
        return taskService.getTaskById(taskId);
    }

    @PostMapping("/{taskId}")
    @PreAuthorize("hasRole('TEACHER')")
    public Task updateTask(@PathVariable int taskId, @RequestBody TaskUpdateRequest taskUpdateRequest) {
        return taskService.updateTask(taskId, taskUpdateRequest);
    }

    @DeleteMapping("/{taskId}")
    @PreAuthorize("hasRole('TEACHER')")
    public void deleteTask(@PathVariable int taskId) {
        taskService.deleteTask(taskId);
    }

    @PostMapping("/{taskId}/solutions")
    public Map<String, Object> addSolution(@RequestHeader("Authorization") String token, @PathVariable int taskId, @RequestParam("files") MultipartFile[] files) {
        String jwt = token.replace("Bearer ", "");
        UserDetails userDetails;

        try {
            userDetails = jwtService.loadUserDetailsFromToken(jwt);
        } catch (Exception e) {
            throw new RuntimeException("Invalid token", e);
        }

        User user = userService.getUserByEmail(userDetails.getUsername());

        return fileSystemStorageService.store(files, user.getId(), taskId);
    }

    @GetMapping("/{taskId}/solutions")
    public Object getSolution(@RequestHeader("Authorization") String token, @PathVariable int taskId) {
        String jwt = token.replace("Bearer ", "");
        UserDetails userDetails;

        try {
            userDetails = jwtService.loadUserDetailsFromToken(jwt);
        } catch (Exception e) {
            throw new RuntimeException("Invalid token", e);
        }

        User user = userService.getUserByEmail(userDetails.getUsername());

        if (user.getRole().equals(Role.ROLE_STUDENT)) {
            CourseMember courseMember = courseMemberRepository.findByTaskAndUser(taskId, user.getId())
                    .orElseThrow(() -> new StorageException("Course member not found."));
            return fileSystemStorageService.getSolutionForStudent(courseMember.getId(), taskId);
        } else if (user.getRole().equals(Role.ROLE_TEACHER)) {
            return fileSystemStorageService.getSolutionForTeacher(taskId);
        } else {
            throw new RuntimeException("Access denied");
        }
    }

    @DeleteMapping("/{taskId}/solutions/{solutionId}")
    public void deleteSolution(@PathVariable int taskId, @PathVariable int solutionId) {
        fileSystemStorageService.deleteSolution(taskId, solutionId);
    }
}
