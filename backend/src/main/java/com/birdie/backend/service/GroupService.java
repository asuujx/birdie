package com.birdie.backend.service;

import com.birdie.backend.entity.Group;
import com.birdie.backend.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    // Create a new group
    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    // Get all groups
    public List<Group>  getAllGroups() {
        return groupRepository.findAll();
    }

    // Get group by ID
    public Optional<Group> getGroupById(int id) {
        return groupRepository.findById(id);
    }

    // Update group
    // TODO

    // Delete all tasks
    public void deleteAllGroups() {
        groupRepository.deleteAll();
    }

    // Delete group
    public void deleteGroup(int id) {
        groupRepository.deleteById(id);
    }
}
