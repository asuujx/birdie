package com.birdie.backend.repositories;

import com.birdie.backend.models.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SolutionRepository extends JpaRepository<Solution, Integer> {
    Optional<Solution> findByIdAndTaskId(int solutionId, int taskId);
    Optional<Solution> findByCourseMemberIdAndTaskId(int courseMemberId, int taskId);
    List<Solution> findAllByTaskId(int taskId);

    @Query("SELECT s FROM Solution s JOIN CourseMember cm ON s.courseMember.id = cm.id WHERE cm.group.id = :groupId AND s.task.id = :taskId")
    Optional<Solution> findByGroupIdAndTaskId(@Param("groupId") int groupId, @Param("taskId") int taskId);
}
