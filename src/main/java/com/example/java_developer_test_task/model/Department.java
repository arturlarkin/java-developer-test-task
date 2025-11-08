package com.example.java_developer_test_task.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Collection;
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

    @ManyToMany
    @JoinTable(
            name = "lector_department",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name = "lector_id")
    )
    private Set<Lector> lectors = new HashSet<>();

    public void addLector(Lector lector) {
        this.lectors.add(lector);
        lector.getDepartments().add(this);
    }

    public void addLectors(Collection<Lector> lectors) {
        for (Lector lector : lectors) {
            addLector(lector);
        }
    }
}
