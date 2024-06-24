package com.birdie.backend.services;

import com.birdie.backend.exceptions.EntityDoesNotExistException;
import com.birdie.backend.models.Course;
import com.birdie.backend.models.CourseMember;
import com.birdie.backend.models.Group;
import com.birdie.backend.repositories.CourseMemberRepository;
import com.birdie.backend.repositories.CourseRepository;
import com.birdie.backend.repositories.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.birdie.backend.config.MessageProvider.COURSE_NOT_FOUND;
import static com.birdie.backend.config.MessageProvider.GROUP_NOT_FOUND;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;
    private final CourseMemberRepository courseMemberRepository;

    public GroupService(GroupRepository groupRepository,
                        CourseRepository courseRepository,
                        CourseMemberRepository courseMemberRepository) {
        this.groupRepository = groupRepository;
        this.courseRepository = courseRepository;
        this.courseMemberRepository = courseMemberRepository;
    }

    public List<Group> getAllGroups(int courseId) {
        return groupRepository.findAllByCourseId(courseId);
    }

    public void createCourseGroup(int courseId, String name) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityDoesNotExistException(COURSE_NOT_FOUND));

        Group group = Group.builder()
                .course(course)
                .name(name)
                .build();

        groupRepository.save(group);
    }

    public void deleteGroup(int courseId, int groupId) {
        List<CourseMember> courseMembers = courseMemberRepository.findByCourseIdAndGroupId(courseId, groupId);

        for(CourseMember courseMember : courseMembers) {
            courseMember.setGroup(null);
            courseMemberRepository.save(courseMember);
        }

        groupRepository.deleteById(groupId);
    }
}
