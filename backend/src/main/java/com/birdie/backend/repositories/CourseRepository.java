package com.birdie.backend.repositories;

import com.birdie.backend.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CourseRepository extends JpaRepository<Course, Integer>, PagingAndSortingRepository<Course, Integer> {
}
