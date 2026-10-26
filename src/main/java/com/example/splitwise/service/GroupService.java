package com.example.splitwise.service;

import com.example.splitwise.Exception.InvalidUsersExp;
import com.example.splitwise.Strategy.OptimalSettleUp;
import com.example.splitwise.Strategy.SettleUpStrategy;
import com.example.splitwise.Strategy.Transation;
import com.example.splitwise.models.Expense;
import com.example.splitwise.models.Group;
import com.example.splitwise.models.User;
import com.example.splitwise.repository.ExpenseRepository;
import com.example.splitwise.repository.GroupRepository;
import com.example.splitwise.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private  final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final ExpenseRepository expenseRepository;

    public GroupService(UserRepository userRepository,GroupRepository groupRepository,ExpenseRepository expenseRepository)
    {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
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

    public  List<Transation> settleUp(Long groupId)
    {
        Group group = groupRepository.findById(groupId).orElseThrow(()-> new InvalidUsersExp("invalid groupId"));
        List<Expense> expenses = expenseRepository.findAllByGroup(group);

        return  new OptimalSettleUp().settleUp(expenses);
    }

}
