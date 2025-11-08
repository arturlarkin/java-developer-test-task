package com.example.java_developer_test_task.service;

import com.example.java_developer_test_task.model.Department;
import com.example.java_developer_test_task.model.Lector;
import com.example.java_developer_test_task.model.LectorDegree;
import com.example.java_developer_test_task.repository.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final LectorService lectorService;

    @Override
    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public Department createDepartment(String name) {
        List<Department> departments = getAllDepartments();

        Set<String> existingNames = new HashSet<>();
        for (Department dep : departments) {
            existingNames.add(dep.getName());
        }

        String finalName = name;
        int counter = 0;
        while (existingNames.contains(finalName)) {
            counter++;
            finalName = name + counter;
        }

        Department department = new Department();
        department.setName(finalName);
        return departmentRepository.save(department);
    }

    @Override
    public Department updateDepartment(Long departmentId, Department department) {
        Department updatedDepartment = getDepartmentById(departmentId);
        updatedDepartment.setName(department.getName());

        if(department.getLectors() != null)
            updatedDepartment.setLectors(department.getLectors());

        updatedDepartment.setHeadOfDepartment(department.getHeadOfDepartment());

        return departmentRepository.save(updatedDepartment);
    }

    @Transactional
    @Override
    public Department assignLector(Long departmentId, Long lectorId) {
        Department department = getDepartmentById(departmentId);
        Lector lector = lectorService.getLectorById(lectorId);

        department.addLector(lector);
        return departmentRepository.save(department);
    }

    @Transactional
    @Override
    public Department assignLectors(Long departmentId, List<Long> lectorIds) {
        Department department = getDepartmentById(departmentId);

        if (lectorIds == null || lectorIds.isEmpty()) {
            throw new IllegalArgumentException("lectorIds cannot be null or empty");
        }

        List<Lector> lectors = lectorIds.stream()
                .map(lectorService::getLectorById)
                .toList();

        department.addLectors(lectors);
        return departmentRepository.save(department);
    }

    @Override
    public Department setHeadOfDepartment(Long departmentId, Long headOfDepartmentId) {
        if (getDepartmentByHeadId(headOfDepartmentId) != null)
            throw new IllegalArgumentException(
                    "Lector with id " + headOfDepartmentId + " is already assigned as head");

        Department department = getDepartmentById(departmentId);
        Lector headOfDepartment = lectorService.getLectorById(headOfDepartmentId);

        if (department.getLectors().contains(headOfDepartment)) {
            department.setHeadOfDepartment(headOfDepartment);
            return departmentRepository.save(department);
        } else throw new IllegalStateException(
                "Lector with id " + headOfDepartmentId + " is not assigned to department " + departmentId);
    }

    @Override
    public Department getDepartmentById(Long departmentId) {
        return departmentRepository.findById(departmentId).orElseThrow(
                () -> new EntityNotFoundException("Department with id " + departmentId + " not found"));
    }

    @Override
    public Department getDepartmentByName(String departmentName) {
        return departmentRepository.findByName(departmentName)
                .orElseThrow(() -> new EntityNotFoundException("Department with name " + departmentName + " not found"));
    }

    @Override
    public Department getDepartmentByHeadId(Long headOfDepartmentId) {
        List<Department> departments = getAllDepartments();

        for (Department department : departments) {
            Lector head = department.getHeadOfDepartment();
            if (head != null && head.getId().equals(headOfDepartmentId)) {
                return department;
            }
        }

        return null;
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public String getHeadOfDepartment(String departmentName) {
        Department department = getDepartmentByName(departmentName);
        Lector head = department.getHeadOfDepartment();

        if (head == null) {
            return "Head of " + departmentName + " is not assigned yet";
        }

        return head.getFullName();
    }

    @Transactional(readOnly = true)
    @Override
    public Map<LectorDegree, Long> getStatistics(String departmentName) {
        Set<Lector> lectors = getDepartmentByName(departmentName).getLectors();
        Map<LectorDegree, Long> stats = new LinkedHashMap<>();

        for (LectorDegree value : LectorDegree.values()) {
            stats.put(value, 0L);
        }

        if (lectors == null || lectors.isEmpty()) return stats;

        for (Lector lector : lectors) {
            if (lector == null) continue;
            LectorDegree key = lector.getDegree();
            if (key != null)
                stats.put(key, stats.getOrDefault(key, 0L) + 1);
        }

        return stats;
    }

    @Transactional(readOnly = true)
    @Override
    public double getAverageSalary(String departmentName) {
        Department department = getDepartmentByName(departmentName);

        double sum = 0.00;
        int count = 0;
        Set<Lector> lectors = department.getLectors();

        if (lectors == null) {
            return 0.00;
        }

        for (Lector lector : lectors) {
            if (lector == null) continue;
            Double value = lector.getSalary();
            if (value != null) {
                sum += value;
                count++;
            }
        }

        double result = count == 0 ? 0.00 : sum / count;
        BigDecimal bd = BigDecimal.valueOf(result);
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }

    @Transactional(readOnly = true)
    @Override
    public int getEmployeeCount(String departmentName) {
        Department department = getDepartmentByName(departmentName);

        if (department.getLectors() == null) {
            return 0;
        }

        return department.getLectors().size();
    }

    @Override
    public void deleteDepartment(Long departmentId) {
        departmentRepository.deleteById(departmentId);
    }
}
