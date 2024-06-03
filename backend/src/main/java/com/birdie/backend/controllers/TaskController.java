package com.birdie.backend.controllers;

import com.birdie.backend.dto.request.TaskUpdateRequest;
import com.birdie.backend.models.Task;
import com.birdie.backend.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
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
}
