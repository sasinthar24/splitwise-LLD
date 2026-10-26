package com.example.splitwise.Strategy;

import com.example.splitwise.models.Expense;

import java.util.List;

public interface SettleUpStrategy {

    List<Transation> settleUp(List<Expense> expenses);
}
