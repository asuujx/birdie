package com.birdie.backend.controller;

import com.birdie.backend.entity.Group;
import com.birdie.backend.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/groups")
public class GroupController {
    @Autowired
    private GroupService groupService;

    // Create a new group
    @PostMapping
    public Group createGroup(@RequestBody Group group) {
        return groupService.createGroup(group);
    }

    // Get all groups
    @GetMapping
    public List<Group> getAllGroups() {
        return groupService.getAllGroups();
    }

    // Get group by ID
    @GetMapping("/{id}")
    public Optional<Group> getGroupById(@PathVariable int id) {
        return groupService.getGroupById(id);
    }

    // Update group by ID
    // TODO

    // Delete all groups
    @DeleteMapping
    public String deleteAllGroups() {
        groupService.deleteAllGroups();
        return "All groups have been deleted successfully.";
    }

    // Delete group by ID
    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable int id) {
        groupService.deleteGroup(id);
    }
}
