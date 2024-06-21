package com.birdie.backend.services;

import com.birdie.backend.dto.response.CourseMemberDetailsResponse;
import com.birdie.backend.models.Course;
import com.birdie.backend.models.CourseMember;
import com.birdie.backend.models.Group;
import com.birdie.backend.models.User;
import com.birdie.backend.models.enummodels.Status;
import com.birdie.backend.repositories.CourseMemberRepository;
import com.birdie.backend.repositories.CourseRepository;
import com.birdie.backend.repositories.GroupRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseMemberService {
    private final CourseMemberRepository courseMemberRepository;
    private final CourseRepository courseRepository;
    private final JwtService jwtService;
    private final UserService userService;
    private final CourseService courseService;
    private final GroupRepository groupRepository;

    public CourseMemberService(CourseMemberRepository courseMemberRepository, CourseRepository courseRepository, JwtService jwtService, UserService userService, CourseService courseService, GroupRepository groupRepository) {
        this.courseMemberRepository = courseMemberRepository;
        this.courseRepository = courseRepository;
        this.jwtService = jwtService;
        this.userService = userService;
        this.courseService = courseService;
        this.groupRepository = groupRepository;
    }

    public CourseMember addUserToCourse(String token, int courseId) {
        String jwt = token.replace("Bearer ", "");
        UserDetails userDetails;
        Course course = courseService.getCourseById(courseId);

        try {
            userDetails = jwtService.loadUserDetailsFromToken(jwt);
        } catch ( Exception e) {
            throw new RuntimeException("Invalid token", e);
        }

        User user = userService.getUserByEmail(userDetails.getUsername());
        Optional<CourseMember> existingCourseMember = courseMemberRepository.findByUserAndCourse(user, course);

        if (existingCourseMember.isPresent()) {
            throw new RuntimeException("User is already a member of this course");
        }

        CourseMember courseMember = CourseMember
                    .builder()
                    .user(user)
                    .course(course)
                    .status(Status.PENDING)
                    .build();

        return courseMemberRepository.save(courseMember);
    }

    public CourseMember editCourseMember(int courseId, int memberId, Map<String, Object> fields) {
        CourseMember courseMember = courseMemberRepository.findByIdAndCourseId(memberId, courseId)
                .orElseThrow(() -> new RuntimeException("Course Member Not Found"));

        if (fields.containsKey("status")) {
            courseMember.setStatus(Status.ACTIVE);
        }

        if (fields.containsKey("groupId")) {
            int groupId = Integer.parseInt(fields.get("groupId").toString());
            Group group = groupRepository.findByIdAndCourseId(groupId, courseId)
                    .orElseThrow(() -> new RuntimeException("Group Not Found"));
            courseMember.setGroup(group);
        }

        return courseMemberRepository.save(courseMember);
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
