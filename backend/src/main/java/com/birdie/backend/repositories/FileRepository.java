package com.birdie.backend.repositories;

import com.birdie.backend.models.File;
import com.birdie.backend.models.Solution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Integer> {
    List<File> findBySolution(Solution solution);
}
