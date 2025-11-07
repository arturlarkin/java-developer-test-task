package com.example.java_developer_test_task.dto;

import com.example.java_developer_test_task.model.LectorDegree;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LectorCredentials {
    private String firstName;
    private String lastName;
    private LectorDegree degree;
    private Double salary;
}
