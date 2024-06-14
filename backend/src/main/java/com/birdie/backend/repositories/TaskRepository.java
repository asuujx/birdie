package com.birdie.backend.repositories;

import com.birdie.backend.models.Course;
import com.birdie.backend.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByCourse(Course course);
    Optional<Task> findById(int id);
}
