package com.birdie.backend.services;

import com.birdie.backend.dto.request.TaskUpdateRequest;
import com.birdie.backend.models.Course;
import com.birdie.backend.models.Task;
import com.birdie.backend.repositories.CourseRepository;
import com.birdie.backend.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CourseRepository courseRepository;

    public List<Task> getAllTasksByCourse(int id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id" + id));

        return taskRepository.findByCourse(course);
    }

    public Task createTask(int courseId, Task task) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));

        task.setCourse(course);

        return taskRepository.save(task);
    }

    public Task getTaskById(int id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id" + id));
    }

    public Task updateTask(int id, TaskUpdateRequest taskUpdateRequest) {
        Task existingTask = getTaskById(id);

        if (taskUpdateRequest.getName() != null) {
            existingTask.setName(taskUpdateRequest.getName());
        }
        if (taskUpdateRequest.getDescription() != null) {
            existingTask.setDescription(taskUpdateRequest.getDescription());
        }
        if (taskUpdateRequest.getDueDate() != null) {
            existingTask.setDueDate(taskUpdateRequest.getDueDate());
        }

        return taskRepository.save(existingTask);
    }

    public void deleteTask(int id) {
        taskRepository.deleteById(id);
    }
}
