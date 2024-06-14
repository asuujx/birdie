package com.birdie.backend.repositories;

import com.birdie.backend.models.Solution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SolutionRepository extends JpaRepository<Solution, Integer> {
    Optional<Solution> findByIdAndTaskId(int solutionId, int taskId);
}
