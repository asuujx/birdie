package com.birdie.backend.services;

import com.birdie.backend.models.Course;
import com.birdie.backend.models.CourseMember;
import com.birdie.backend.models.User;
import com.birdie.backend.models.enummodels.Status;
import com.birdie.backend.repositories.CourseMemberRepository;
import com.birdie.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseMemberService {
    @Autowired
    private CourseMemberRepository courseMemberRepository;

    public CourseMember addUserToCourse(User user, Course course) {
        CourseMember courseMember = CourseMember
                .builder()
                .user(user)
                .course(course)
                .status(Status.PENDING)
                .build();

        return courseMemberRepository.save(courseMember);
    }

    public CourseMember approveMember(CourseMember courseMember) {
        courseMember.setStatus(Status.ACTIVE);

        return courseMemberRepository.save(courseMember);
    }

    public CourseMember getCourseMemberById(int courseMemberId) {
        return courseMemberRepository.findById(courseMemberId)
                .orElseThrow(() -> new RuntimeException("CourseMember not found"));
    }
}
