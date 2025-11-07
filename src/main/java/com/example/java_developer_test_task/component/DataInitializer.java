package com.example.java_developer_test_task.component;

import com.example.java_developer_test_task.dto.LectorCredentials;
import com.example.java_developer_test_task.model.Department;
import com.example.java_developer_test_task.model.Lector;
import com.example.java_developer_test_task.service.DepartmentService;
import com.example.java_developer_test_task.service.LectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.java_developer_test_task.model.LectorDegree.*;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final LectorService lectorService;
    private final DepartmentService departmentService;

    @Transactional
    @Override
    public void run(String... args) {
        Lector l1 = lectorService.createLector(new LectorCredentials(
                "Andrii", "Bondar", ASSISTANT, 400.00));
        Lector l2 = lectorService.createLector(new LectorCredentials(
                "Olena", "Shulga", ASSISTANT, 450.00));
        Lector l3 = lectorService.createLector(new LectorCredentials(
                "Dmytro", "Ostapchenko", ASSOCIATE_PROFESSOR, 600.00));
        Lector l4 = lectorService.createLector(new LectorCredentials(
                "Kateryna", "Verbytska", ASSOCIATE_PROFESSOR, 700.00));
        Lector l5 = lectorService.createLector(new LectorCredentials(
                "Natalia", "Zhuravel", PROFESSOR, 900.00));
        Lector l6 = lectorService.createLector(new LectorCredentials(
                "Oleksandr", "Makovskyi", PROFESSOR, 950.00));

        Department physics = departmentService.createDepartment("Physics");
        Department math = departmentService.createDepartment("Mathematics");
        Department economics = departmentService.createDepartment("Economics");

        departmentService.assignLectors(physics.getId(), List.of(l1.getId(), l3.getId(), l4.getId()));
        departmentService.assignLectors(math.getId(), List.of(l1.getId(), l2.getId(), l3.getId(), l5.getId()));
        departmentService.assignLectors(economics.getId(), List.of(l3.getId(), l4.getId(), l5.getId(), l6.getId()));

        departmentService.setHeadOfDepartment(physics.getId(), l3.getId());
        departmentService.setHeadOfDepartment(math.getId(), l5.getId());
        departmentService.setHeadOfDepartment(economics.getId(), l6.getId());
    }
}
