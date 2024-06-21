package com.birdie.backend.services;

import com.birdie.backend.models.Course;
import com.birdie.backend.models.CourseMember;
import com.birdie.backend.models.Group;
import com.birdie.backend.repositories.CourseMemberRepository;
import com.birdie.backend.repositories.CourseRepository;
import com.birdie.backend.repositories.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;
    private final CourseMemberRepository courseMemberRepository;

    public GroupService(GroupRepository groupRepository, CourseRepository courseRepository, CourseMemberRepository courseMemberRepository) {
        this.groupRepository = groupRepository;
        this.courseRepository = courseRepository;
        this.courseMemberRepository = courseMemberRepository;
    }

    public List<Group> getAllGroups(int courseId) {
        return groupRepository.findAllByCourseId(courseId);
    }

    public Group addCourseGroup(int courseId, String name) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        Group group = Group.builder()
                .course(course)
                .name(name)
                .build();

        return groupRepository.save(group);
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
