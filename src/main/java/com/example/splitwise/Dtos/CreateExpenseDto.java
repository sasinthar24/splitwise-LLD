package com.example.splitwise.Dtos;


import lombok.Data;

import java.util.Map;

@Data
public class CreateExpenseDto {
    String name;
    double amount;
    long group_id;
    Map<Long,Double> paidBy;
    Map<Long,Double> owedBy;

}
