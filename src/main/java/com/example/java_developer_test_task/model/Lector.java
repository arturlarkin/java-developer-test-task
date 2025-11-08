package com.example.java_developer_test_task.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"departments"})
@EqualsAndHashCode(exclude = {"departments"})
@Entity
@Table(name = "lectors")
public class Lector {
    @Id
    @Column(name = "lector_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "degree")
    private LectorDegree degree;

    @Column(name = "salary")
    private Double salary;

    @ManyToMany(mappedBy = "lectors")
    @JsonIgnore
    private Set<Department> departments = new HashSet<>();

    public String getFullName() {
        String fullName;
        fullName = (firstName == null ? "" : firstName)
                + (firstName != null && lastName != null ? " " : "")
                + (lastName == null ? "" : lastName);

        return fullName;
    }
}
