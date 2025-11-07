package com.example.java_developer_test_task.service;

import com.example.java_developer_test_task.dto.LectorCredentials;
import com.example.java_developer_test_task.model.Lector;

import java.util.List;

public interface LectorService {
    Lector save(Lector lector);
    Lector createLector(LectorCredentials credentials);
    Lector updateLector(Long lectorId, Lector lector);
    Lector updateLector(Long lectorId, LectorCredentials credentials);
    List<Lector> getAllLectors();
    Lector getLectorById(Long lectorId);
    String getNamesByTemplate(String template);
    void deleteLector(Long lectorId);
}
