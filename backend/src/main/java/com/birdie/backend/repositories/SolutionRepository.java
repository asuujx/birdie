package com.birdie.backend.repositories;

import com.birdie.backend.models.Solution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolutionRepository extends JpaRepository<Solution, Integer> {
}
