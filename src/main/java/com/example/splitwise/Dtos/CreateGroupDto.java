package com.example.splitwise.Dtos;

import lombok.Data;

import java.util.List;

@Data
public class CreateGroupDto {
    String name;
    List<Long> userIds;


}
