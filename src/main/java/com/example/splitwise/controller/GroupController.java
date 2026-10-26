package com.example.splitwise.controller;

import com.example.splitwise.Dtos.CreateGroupDto;
import com.example.splitwise.models.Group;
import com.example.splitwise.service.GroupService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("group")
public class GroupController {


    private final GroupService groupService;

    public GroupController(GroupService groupService)
    {
        this.groupService = groupService;
    }
    @PostMapping("")
    public Group createGroup(@RequestBody CreateGroupDto createGroupDto)
    {
        return groupService.createGroup(createGroupDto.getName(),createGroupDto.getUserIds());
    }

}
