package com.birdie.backend.services;

import com.birdie.backend.dto.response.CourseMemberDetailsResponse;
import com.birdie.backend.models.Course;
import com.birdie.backend.models.CourseMember;
import com.birdie.backend.models.User;
import com.birdie.backend.models.enummodels.Status;
import com.birdie.backend.repositories.CourseMemberRepository;
import com.birdie.backend.repositories.CourseRepository;
import com.birdie.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseMemberService {
    @Autowired
    private CourseMemberRepository courseMemberRepository;

    @Autowired
    private CourseRepository courseRepository;

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

    public void deleteCourseMember(int courseId, int memberId) {
        CourseMember courseMember = courseMemberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("CourseMember not found with id" + memberId));

        if (courseMember.getCourse().getId() != courseId) {
            throw new RuntimeException("CourseMember does not belong to the specified course");
        }

        courseMemberRepository.delete(courseMember);
    }

    public List<CourseMemberDetailsResponse> getCourseMembers(int courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id " + courseId));

        List<CourseMember> courseMembers = courseMemberRepository.findByCourse(course);

        return courseMembers.stream().map(this::mapToCourseMemberDetailsResponse).collect(Collectors.toList());
    }

    private CourseMemberDetailsResponse mapToCourseMemberDetailsResponse(CourseMember courseMember) {
        CourseMemberDetailsResponse response = new CourseMemberDetailsResponse();
        response.setId(courseMember.getId());
        response.setName(courseMember.getUser().getName());
        response.setSurname(courseMember.getUser().getSurname());
        response.setGroup(courseMember.getGroup() != null ? courseMember.getGroup().getId() : null);
        return response;
    }
}
