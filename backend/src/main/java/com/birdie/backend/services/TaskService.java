package com.birdie.backend.services;

import com.birdie.backend.config.MessageProvider;
import com.birdie.backend.dto.request.TaskUpdateRequest;
import com.birdie.backend.exceptions.EntityDoesNotExistException;
import com.birdie.backend.models.Course;
import com.birdie.backend.models.Task;
import com.birdie.backend.repositories.CourseRepository;
import com.birdie.backend.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final CourseRepository courseRepository;

    public TaskService(TaskRepository taskRepository,
                       CourseRepository courseRepository) {
        this.taskRepository = taskRepository;
        this.courseRepository = courseRepository;
    }

    public List<Task> getAllTasksByCourse(int id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityDoesNotExistException(MessageProvider.COURSE_NOT_FOUND));

        return taskRepository.findByCourse(course);
    }

    public void createTask(int courseId, Task task) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityDoesNotExistException(MessageProvider.COURSE_NOT_FOUND));

        task.setCourse(course);

        taskRepository.save(task);
    }

    public Task getTaskById(int id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityDoesNotExistException(MessageProvider.TASK_NOT_FOUND));
    }

    public void updateTask(int id, TaskUpdateRequest taskUpdateRequest) {
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

        taskRepository.save(existingTask);
    }

    public void deleteTask(int id) {
        taskRepository.deleteById(id);
    }
}
