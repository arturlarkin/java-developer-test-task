package com.example.java_developer_test_task.component;

import com.example.java_developer_test_task.model.LectorDegree;
import com.example.java_developer_test_task.service.DepartmentService;
import com.example.java_developer_test_task.service.LectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
class ConsoleInterface implements CommandLineRunner {

    private final LectorService lectorService;
    private final DepartmentService departmentService;

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to University CLI! Type 'exit' to quit.\n");

        while (true) {
            System.out.print("Enter command: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Bye!");
                break;
            }

            try {
                handleCommand(input);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void handleCommand(String input) {
        if (input.startsWith("Who is head of department")) {
            String department = input.replace("Who is head of department", "").trim();
            String head = departmentService.getHeadOfDepartment(department);
            System.out.printf("Head of %s is %s%n", department, head);

        } else if (input.startsWith("Show") && input.contains("statistics")) {
            String department = input.replace("Show", "").replace("statistics", "").trim();
            Map<LectorDegree, Long> stats = departmentService.getStatistics(department);
            stats.forEach((degree, count) ->
                    System.out.printf("%s - %d%n", degree.name().toLowerCase().replace('_', ' '), count));

        } else if (input.startsWith("Show the average salary for the department")) {
            String department = input.replace("Show the average salary for the department", "").trim();
            double avg = departmentService.getAverageSalary(department);
            System.out.printf("The average salary for the %s department is %.2f%n", department, avg);

        } else if (input.startsWith("Show count of employee for")) {
            String department = input.replace("Show count of employee for", "").trim();
            int count = departmentService.getEmployeeCount(department);
            System.out.printf("The %s department has %d employee(s)%n", department, count);

        } else if (input.startsWith("Global search by")) {
            String template = input.replace("Global search by", "").trim();
            String names = lectorService.getNamesByTemplate(template);
            System.out.println(names);

        } else {
            System.out.println("Unknown command. Please try again.");
        }
    }
}
