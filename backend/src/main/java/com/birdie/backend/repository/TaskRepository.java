package com.birdie.backend.repository;

import com.birdie.backend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TaskRepository extends JpaRepository<Task, Integer> {
}
