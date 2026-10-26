package com.example.splitwise.service;

import com.example.splitwise.Exception.InvalidUsersExp;
import com.example.splitwise.models.Group;
import com.example.splitwise.models.User;
import com.example.splitwise.repository.GroupRepository;
import com.example.splitwise.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private  final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public GroupService(UserRepository userRepository,GroupRepository groupRepository)
    {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }


    public Group createGroup(String name, List<Long> userIds)
    {
        List<User> userList = userRepository.findAllById(userIds);

        if(userIds.size()!=userList.size())
        {
              throw new InvalidUsersExp("Invalid UserIds");
        }

        Group group = new Group(name,userList);


        return  groupRepository.save(group);

    }

}
