package com.example.java_developer_test_task.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"lectors"})
@EqualsAndHashCode(exclude = {"lectors"})
@Entity
@Table(name = "departments")
public class Department {
    @Id
    @Column(name = "department_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @OneToOne
    @JoinColumn(name = "head_of_department_id", referencedColumnName = "lector_id", unique = true)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Lector headOfDepartment;

    @JsonIgnore
    @ManyToMany(mappedBy = "departments", fetch = FetchType.LAZY)
    private Set<Lector> lectors = new HashSet<>();
}
