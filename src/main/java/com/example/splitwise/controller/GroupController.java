package com.example.splitwise.controller;

import com.example.splitwise.Dtos.CreateGroupDto;
import com.example.splitwise.Strategy.Transation;
import com.example.splitwise.models.Group;
import com.example.splitwise.service.GroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @GetMapping("/{id}/settleup")
    public List<Transation> settleTransations(@PathVariable(name = "id") Long groupId)
    {
        return groupService.settleUp(groupId);
    }

}
