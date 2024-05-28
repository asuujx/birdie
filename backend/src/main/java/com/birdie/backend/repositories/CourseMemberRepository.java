package com.birdie.backend.repositories;

import com.birdie.backend.models.Course;
import com.birdie.backend.models.CourseMember;
import com.birdie.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseMemberRepository extends JpaRepository<CourseMember, Integer> {
    List<CourseMember> findByUser(User user);
    List<CourseMember> findByCourse(Course course);
}
