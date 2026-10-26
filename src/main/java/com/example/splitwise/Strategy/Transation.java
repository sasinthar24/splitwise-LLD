package com.example.splitwise.Strategy;

import com.example.splitwise.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Transation {

    double amount;
    User paidBy;
    User paidTo;
}
