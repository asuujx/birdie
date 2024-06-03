package com.birdie.backend.controllers;

import com.birdie.backend.dto.request.TaskUpdateRequest;
import com.birdie.backend.models.Task;
import com.birdie.backend.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable int id) {
        return taskService.getTaskById(id);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public Task updateTask(@PathVariable int id, @RequestBody TaskUpdateRequest taskUpdateRequest) {
        return taskService.updateTask(id, taskUpdateRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public void deleteTask(@PathVariable int id) {
        taskService.deleteTask(id);
    }
}
