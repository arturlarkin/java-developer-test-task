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
        String normalizedInput = input.replaceAll("[.,!?]", "").trim();
        String lowerInput = normalizedInput.toLowerCase();

        if (lowerInput.startsWith("who is head of department")) {
            String departmentName = normalizedInput.substring("Who is head of department".length()).trim();
            String head = departmentService.getHeadOfDepartment(departmentName);
            System.out.printf("Head of %s is %s%n", departmentName, head);

        } else if (lowerInput.startsWith("show") && lowerInput.contains("statistics")) {
            String departmentName = normalizedInput
                    .replaceAll("(?i)show", "")
                    .replaceAll("(?i)statistics", "")
                    .trim();
            Map<LectorDegree, Long> stats = departmentService.getStatistics(departmentName);
            stats.forEach((degree, count) ->
                    System.out.printf("%s - %d%n", degree.name().toLowerCase()
                            .replace('_', ' '), count));

        } else if (lowerInput.startsWith("show the average salary for the department")) {
            String departmentName = normalizedInput
                    .substring("Show the average salary for the department".length()).trim();
            double avg = departmentService.getAverageSalary(departmentName);
            System.out.printf("The average salary for the %s department is $%.2f%n", departmentName, avg);

        } else if (lowerInput.startsWith("show count of employee for")) {
            String departmentName = normalizedInput.substring("Show count of employee for".length()).trim();
            int count = departmentService.getEmployeeCount(departmentName);
            System.out.printf("The %s department has %d employee(s)%n", departmentName, count);

        } else if (lowerInput.startsWith("global search by")) {
            String template = normalizedInput.substring("Global search by".length()).trim();
            String names = lectorService.getNamesByTemplate(template);
            System.out.println(names);

        } else {
            System.out.println("Unknown command. Please try again.");
        }
    }
}
