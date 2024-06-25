package com.birdie.backend.repositories;

import com.birdie.backend.models.Course;
import com.birdie.backend.models.CourseMember;
import com.birdie.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseMemberRepository extends JpaRepository<CourseMember, Integer> {
    List<CourseMember> findByUser(User user);
    List<CourseMember> findByCourse(Course course);
    List<CourseMember> findByCourseIdAndGroupId(int courseId, int groupId);
    Optional<CourseMember> findByIdAndCourseId(int id, int courseId);
    Optional<CourseMember> findByUserAndCourse(User user, Course course);

    @Query("SELECT cm FROM CourseMember cm JOIN cm.course c JOIN Task t ON t.course.id = c.id WHERE t.id = ?1 AND cm.user.id = ?2")
    Optional<CourseMember> findByTaskAndUser(int taskId, int userId);

    @Query("SELECT cm FROM CourseMember cm WHERE cm.course.id = :courseId AND cm.user.id = :userId")
    Optional<CourseMember> findByCourseIdAndUserId(int courseId, int userId);
}
