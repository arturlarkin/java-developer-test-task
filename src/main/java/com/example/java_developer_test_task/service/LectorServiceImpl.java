package com.example.java_developer_test_task.service;

import com.example.java_developer_test_task.dto.LectorCredentials;
import com.example.java_developer_test_task.model.Lector;
import com.example.java_developer_test_task.repository.LectorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LectorServiceImpl implements LectorService {
    private final LectorRepository lectorRepository;

    @Transactional
    @Override
    public Lector save(Lector lector) {
        return lectorRepository.save(lector);
    }

    @Override
    public Lector createLector(LectorCredentials credentials) {
        Lector lector = new Lector();
        lector.setFirstName(credentials.getFirstName());
        lector.setLastName(credentials.getLastName());
        lector.setDegree(credentials.getDegree());
        lector.setSalary(credentials.getSalary());

        return lectorRepository.save(lector);
    }

    @Override
    public Lector updateLector(Long lectorId, Lector lector) {
        Lector updatedLector = getLectorById(lectorId);
        updatedLector.setFirstName(lector.getFirstName());
        updatedLector.setLastName(lector.getLastName());
        updatedLector.setDegree(lector.getDegree());
        updatedLector.setSalary(lector.getSalary());

        if(lector.getDepartments() != null)
            updatedLector.setDepartments(lector.getDepartments());

        return lectorRepository.save(updatedLector);
    }

    @Override
    public Lector updateLector(Long lectorId, LectorCredentials credentials) {
        Lector updatedLector = getLectorById(lectorId);
        updatedLector.setFirstName(credentials.getFirstName());
        updatedLector.setLastName(credentials.getLastName());
        updatedLector.setDegree(credentials.getDegree());
        updatedLector.setSalary(credentials.getSalary());

        return lectorRepository.save(updatedLector);
    }

    @Override
    public List<Lector> getAllLectors() {
        return lectorRepository.findAll();
    }

    @Override
    public Lector getLectorById(Long lectorId) {
        return lectorRepository.findById(lectorId).orElseThrow(
                () -> new EntityNotFoundException("Lector with id " + lectorId + " not found"));
    }

    @Override
    public String getNamesByTemplate(String template) {
        List<Lector> lectors = getAllLectors();
        StringBuilder result = new StringBuilder();
        String lowerTemplate = template.toLowerCase();

        for (Lector lector : lectors) {
            if (lector == null || lector.getFullName().isEmpty()) {
                continue;
            }

            String fullName = lector.getFullName();
            if (fullName.toLowerCase().contains(lowerTemplate)) {
                if (!result.isEmpty()) {
                    result.append(", ");
                }
                result.append(fullName);
            }
        }

        return result.toString();
    }

    @Override
    public void deleteLector(Long lectorId) {
        lectorRepository.deleteById(lectorId);
    }
}
