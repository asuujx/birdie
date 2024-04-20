package com.birdie.backend.service;

import com.birdie.backend.entity.Task;
import com.birdie.backend.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    // Create a new task
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // Get all tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Get task by ID
    public Optional<Task> getTaskById(int id) {
        return taskRepository.findById(id);
    }

    // Update task
    // TODO

    // Delete all tasks
    public void deleteAllTasks() {
        taskRepository.deleteAll();
    }

    // Delete task
    public void deleteTask(int id) {
        taskRepository.deleteById(id);
    }
}
