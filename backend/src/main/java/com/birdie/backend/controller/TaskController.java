package com.birdie.backend.controller;

import com.birdie.backend.entity.Task;
import com.birdie.backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    // Create a new task
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    // Get all tasks
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    // Get task by ID
    @GetMapping("/{id}")
    public Optional<Task> getTaskById(@PathVariable int id) {
        return taskService.getTaskById(id);
    }

    // Update task by ID
    // TODO

    // Delete all tasks
    @DeleteMapping
    public String deleteAllTasks() {
        taskService.deleteAllTasks();
        return "All tasks have been deleted successfully";
    }

    // Delete task by ID
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable int id) {
        taskService.deleteTask(id);
    }
}
