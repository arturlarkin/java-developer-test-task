package com.example.java_developer_test_task.controller;

import com.example.java_developer_test_task.model.Department;
import com.example.java_developer_test_task.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping("/create")
    public ResponseEntity<Department> createDepartment(@RequestParam String name) {
        Department department = departmentService.createDepartment(name);
        return new ResponseEntity<>(department, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long departmentId) {
        return ResponseEntity.ok(departmentService.getDepartmentById(departmentId));
    }

    @GetMapping("/head")
    public ResponseEntity<String> getHeadOfDepartment(@RequestParam String departmentName) {
        return ResponseEntity.ok(departmentService.getHeadOfDepartment(departmentName));
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStatistics(@RequestParam String departmentName) {
        return ResponseEntity.ok(departmentService.getStatistics(departmentName));
    }

    @GetMapping("/salary")
    public ResponseEntity<?> getAverageSalary(@RequestParam String departmentName) {
        return ResponseEntity.ok(departmentService.getAverageSalary(departmentName));
    }

    @GetMapping("/employees")
    public ResponseEntity<?> getEmployeeCount(@RequestParam String departmentName) {
        return ResponseEntity.ok(departmentService.getEmployeeCount(departmentName));
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<Department> assignLectors(@PathVariable Long departmentId,
                                                    @RequestBody List<Long> lectorIds) {
        Department updatedDepartment = departmentService.assignLectors(departmentId, lectorIds);
        return ResponseEntity.ok(updatedDepartment);
    }

    @PutMapping("/{departmentId}/head/{headOfDepartmentId}")
    public ResponseEntity<Department> setHeadOfDepartment(@PathVariable Long departmentId,
                                                    @PathVariable Long headOfDepartmentId) {
        Department updatedDepartment = departmentService.setHeadOfDepartment(departmentId, headOfDepartmentId);
        return ResponseEntity.ok(updatedDepartment);
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long departmentId) {
        departmentService.deleteDepartment(departmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
