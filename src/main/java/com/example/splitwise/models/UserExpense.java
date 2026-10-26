package com.example.splitwise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Comparator;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_expense")
public class UserExpense extends BaseModel implements Comparable<UserExpense> {


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    double amount;

    public UserExpense(User user, Double amount) {
        this.user = user;
        this.amount = amount;
    }


    @Override
    public int compareTo(UserExpense userExpense) {
        return Double.compare(amount,userExpense.amount);
    }
}