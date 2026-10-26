package com.example.splitwise.service;

import com.example.splitwise.Dtos.CreateExpenseDto;
import com.example.splitwise.Dtos.CreateGroupDto;
import com.example.splitwise.Exception.InvalidUsersExp;
import com.example.splitwise.models.Expense;
import com.example.splitwise.models.Group;
import com.example.splitwise.models.User;
import com.example.splitwise.models.UserExpense;
import com.example.splitwise.repository.ExpenseRepository;
import com.example.splitwise.repository.GroupRepository;
import com.example.splitwise.repository.UserExpenseRepository;
import com.example.splitwise.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final UserExpenseRepository userExpenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository,UserRepository userRepository, GroupRepository groupRepository,UserExpenseRepository userExpenseRepository)
    {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.userExpenseRepository = userExpenseRepository;
    }
     @Transactional
    public Expense createExpense(CreateExpenseDto createExpenseDto)
    {
        //paid by users
//        Set<Long> paidByUserIds = createExpenseDto.getPaidBy().keySet();
//        List<User> paidByUserList = userRepository.findAllById(paidByUserIds);
//
//        if(paidByUserIds.size()!=paidByUserList.size())
//        {
//            throw new InvalidUsersExp("Invalid UserIds");
//        }
//
//        //owed by users
//        Set<Long> owedByUserIds = createExpenseDto.getOwedBy().keySet();
//        List<User> owedByUserList = userRepository.findAllById(paidByUserIds);
//
//        if(owedByUserIds.size()!=owedByUserList.size())
//        {
//            throw new InvalidUsersExp("Invalid UserIds");
//        }


        //group to which the expense attached to
        Optional<Group> groupOptional = groupRepository.findById(createExpenseDto.getGroup_id());

        if(groupOptional.isEmpty())
            throw new InvalidUsersExp("Group not Found");




        List<UserExpense> paidByUsersExpenses = new ArrayList<>();
        for(Map.Entry<Long,Double> entry: createExpenseDto.getPaidBy().entrySet())
        {
            Long userId = entry.getKey();
            double amount = entry.getValue();
            Optional<User> optionalUser = userRepository.findById(userId);
            if(optionalUser.isEmpty())
                throw new InvalidUsersExp("User not Exist");

            User user = optionalUser.get();
            UserExpense userExpense = new UserExpense(user,amount);
            paidByUsersExpenses.add(userExpense);
        }
        List<UserExpense> paidByUsers = userExpenseRepository.saveAll(paidByUsersExpenses);

        List<UserExpense> owedByUsersExpenses = new ArrayList<>();
        for(Map.Entry<Long,Double> entry: createExpenseDto.getOwedBy().entrySet())
        {
            Long userId = entry.getKey();
            double amount = entry.getValue();
            Optional<User> optionalUser = userRepository.findById(userId);
            if(optionalUser.isEmpty())
                throw new InvalidUsersExp("User not Exist");

            User user = optionalUser.get();
            UserExpense userExpense = new UserExpense(user,-amount);
            owedByUsersExpenses.add(userExpense);
        }
      List<UserExpense> owedByUsers =  userExpenseRepository.saveAll(owedByUsersExpenses);

        Expense expense = Expense.builder()
                .name(createExpenseDto.getName())
                .group(groupOptional.get())
                .amount(createExpenseDto.getAmount())
                .owedBy(owedByUsers)
                .paidBy(paidByUsers)
                .build();
        return expenseRepository.save(expense);
    }
}
