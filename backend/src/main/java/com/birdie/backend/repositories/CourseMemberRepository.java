package com.birdie.backend.repositories;

import com.birdie.backend.models.CourseMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseMemberRepository extends JpaRepository<CourseMember, Integer> {

}
