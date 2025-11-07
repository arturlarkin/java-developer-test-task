package com.example.java_developer_test_task.service;

import com.example.java_developer_test_task.model.Department;
import com.example.java_developer_test_task.model.LectorDegree;

import java.util.List;
import java.util.Map;

public interface DepartmentService {
    Department createDepartment(Department department);
    Department createDepartment(String name);
    Department updateDepartment(Long departmentId, Department department);
    Department assignLector(Long departmentId, Long lectorId);
    Department assignLectors(Long departmentId, List<Long> lectorIds);
    Department setHeadOfDepartment(Long departmentId, Long headOfDepartmentId);
    Department getDepartmentById(Long departmentId);
    Department getDepartmentByName(String departmentName);
    Department getDepartmentByHeadId(Long headOfDepartmentId);
    List<Department> getAllDepartments();
    String getHeadOfDepartment(String departmentName);
    Map<LectorDegree, Long> getStatistics(String departmentName);
    double getAverageSalary(String departmentName);
    int getEmployeeCount(String departmentName);
    void deleteDepartment(Long departmentId);
}
