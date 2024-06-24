package com.birdie.backend.services;

import com.birdie.backend.dto.response.CourseMemberDetailsResponse;
import com.birdie.backend.exceptions.EntityAlreadyExistException;
import com.birdie.backend.exceptions.EntityDoesNotExistException;
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

import static com.birdie.backend.config.MessageProvider.*;

@Service
public class CourseMemberService {
    private final CourseMemberRepository courseMemberRepository;
    private final CourseRepository courseRepository;
    private final JwtService jwtService;
    private final UserService userService;
    private final CourseService courseService;
    private final GroupRepository groupRepository;

    public CourseMemberService(CourseMemberRepository courseMemberRepository,
                               CourseRepository courseRepository,
                               JwtService jwtService,
                               UserService userService,
                               CourseService courseService,
                               GroupRepository groupRepository) {
        this.courseMemberRepository = courseMemberRepository;
        this.courseRepository = courseRepository;
        this.jwtService = jwtService;
        this.userService = userService;
        this.courseService = courseService;
        this.groupRepository = groupRepository;
    }

    public void addUserToCourse(String token, int courseId) {
        String jwt = token.replace("Bearer ", "");
        UserDetails userDetails;
        Course course = courseService.getCourse(courseId);

        try {
            userDetails = jwtService.loadUserDetailsFromToken(jwt);
        } catch ( Exception e) {
            throw new IllegalArgumentException(TOKEN_INVALID);
        }

        User user = userService.getUserByEmail(userDetails.getUsername());
        Optional<CourseMember> existingCourseMember = courseMemberRepository.findByUserAndCourse(user, course);

        if (existingCourseMember.isPresent()) {
            throw new EntityAlreadyExistException(COURSE_MEMBER_EXIST);
        }

        CourseMember courseMember = CourseMember
                    .builder()
                    .user(user)
                    .course(course)
                    .status(Status.PENDING)
                    .build();

        courseMemberRepository.save(courseMember);
    }

    public void editCourseMember(int courseId, int memberId, Map<String, Object> fields) {
        CourseMember courseMember = courseMemberRepository.findByIdAndCourseId(memberId, courseId)
                .orElseThrow(() -> new EntityDoesNotExistException(COURSE_MEMBER_NOT_FOUND));

        if (fields.containsKey("status")) {
            courseMember.setStatus(Status.ACTIVE);
        }

        if (fields.containsKey("groupId")) {
            int groupId = Integer.parseInt(fields.get("groupId").toString());
            Group group = groupRepository.findByIdAndCourseId(groupId, courseId)
                    .orElseThrow(() -> new EntityDoesNotExistException(GROUP_NOT_FOUND));

            courseMember.setGroup(group);
        }

        courseMemberRepository.save(courseMember);
    }

    public void deleteCourseMember(int courseId, int memberId) {
        CourseMember courseMember = courseMemberRepository.findById(memberId)
                .orElseThrow(() -> new EntityDoesNotExistException(COURSE_MEMBER_NOT_FOUND));

        if (courseMember.getCourse().getId() != courseId) {
            throw new EntityDoesNotExistException(COURSE_MEMBER_NOT_FOUND);
        }

        courseMemberRepository.delete(courseMember);
    }

    public List<CourseMemberDetailsResponse> getCourseMembers(int courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityDoesNotExistException(COURSE_NOT_FOUND));

        List<CourseMember> courseMembers = courseMemberRepository.findByCourse(course);

        return courseMembers.stream()
                .map(this::mapToCourseMemberDetailsResponse)
                .collect(Collectors.toList());
    }

    private CourseMemberDetailsResponse mapToCourseMemberDetailsResponse(CourseMember courseMember) {
        CourseMemberDetailsResponse response = new CourseMemberDetailsResponse();

        response.setId(courseMember.getId());
        response.setName(courseMember.getUser().getName());
        response.setSurname(courseMember.getUser().getSurname());
        response.setGroupId(courseMember.getGroup() != null ? courseMember.getGroup().getId() : null);
        response.setGroupName(courseMember.getGroup() != null ? courseMember.getGroup().getName() : null);

        return response;
    }
}
