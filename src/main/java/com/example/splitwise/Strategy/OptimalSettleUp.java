package com.example.splitwise.Strategy;

import com.example.splitwise.models.Expense;
import com.example.splitwise.models.User;
import com.example.splitwise.models.UserExpense;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;

import java.util.*;

/**
 *
 * Steps to settle up
 * 1.For each user find out the amount they need to pay or get back.
 * 2.Create 2 priorty queues. 1 with maxPaying and 1 with max Reciving
 * 3. Match the biggest ones , settle one of them
 *
 */

public class OptimalSettleUp implements SettleUpStrategy{
    @Override
    public List<Transation> settleUp(List<Expense> expenses) {

        Map<User, Double> owings = getOwings(expenses);

        PriorityQueue<UserExpense> lender = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<UserExpense> borrower = new PriorityQueue<>(Collections.reverseOrder());

        owings.forEach((user, amount) -> {
            if(amount < 0)
            {
                lender.add(new UserExpense(user,-amount));
            }
            else
            {
                borrower.add(new UserExpense(user,amount));
            }
        } );

        List<Transation> transations = new ArrayList<>();
        while (!lender.isEmpty())
        {
            UserExpense topLender = lender.poll();
            UserExpense topBorrower = borrower.poll();
            if(topLender.getAmount() > topBorrower.getAmount())
            {
                transations.add(new Transation(topBorrower.getAmount(),topBorrower.getUser(),topLender.getUser()));
                lender.add(new UserExpense(topLender.getUser(),topLender.getAmount() - topBorrower.getAmount()));
            }
            else if(topLender.getAmount() == topBorrower.getAmount())
            {
                transations.add(new Transation(topBorrower.getAmount(),topBorrower.getUser(),topLender.getUser()));
            }
            else
            {
                transations.add(new Transation(topLender.getAmount(),topBorrower.getUser(),topLender.getUser()));
                borrower.add(new UserExpense(topBorrower.getUser(),topBorrower.getAmount() - topLender.getAmount()));


            }
        }





        return transations;
    }

    private Map<User, Double> getOwings(List<Expense> expenses) {
        Map<User,Double> owings = new HashMap<>();

        for(Expense expense: expenses)
        {
            for (UserExpense userExpense: expense.getPaidBy())
            {
                if (!owings.containsKey(userExpense.getUser()))
                {
                    owings.put(userExpense.getUser(), 0.0);
                }
                owings.put(userExpense.getUser(),owings.get(userExpense.getUser())+userExpense.getAmount());

            }
            for (UserExpense userExpense: expense.getOwedBy())
            {
                if (!owings.containsKey(userExpense.getUser()))
                {
                    owings.put(userExpense.getUser(), 0.0);
                }
                owings.put(userExpense.getUser(),owings.get(userExpense.getUser())+userExpense.getAmount());

            }
        }
        return owings;
    }
}
