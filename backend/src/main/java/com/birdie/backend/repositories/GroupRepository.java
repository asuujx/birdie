package com.birdie.backend.repositories;

import com.birdie.backend.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Integer> {
    List<Group> findAllByCourseId(int courseId);
    Optional<Group> findByIdAndCourseId(int id, int courseId);
}
