package com.birdie.backend.repositories;

import com.birdie.backend.models.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Integer> {
}
