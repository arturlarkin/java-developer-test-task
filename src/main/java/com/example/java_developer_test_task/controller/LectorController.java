package com.example.java_developer_test_task.controller;

import com.example.java_developer_test_task.dto.LectorCredentials;
import com.example.java_developer_test_task.model.Lector;
import com.example.java_developer_test_task.service.LectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lectors")
@RequiredArgsConstructor
@CrossOrigin("*")
public class LectorController {
    private final LectorService lectorService;

    @PostMapping("/create")
    public ResponseEntity<Lector> createLector(@RequestBody LectorCredentials credentials) {
        Lector lector = lectorService.createLector(credentials);
        return new ResponseEntity<>(lector, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllLectors() {
        return ResponseEntity.ok(lectorService.getAllLectors());
    }

    @GetMapping("/{lectorId}")
    public ResponseEntity<Lector> getLectorById(@PathVariable Long lectorId) {
        return ResponseEntity.ok(lectorService.getLectorById(lectorId));
    }

    @GetMapping("/search")
    public ResponseEntity<String> getNamesByTemplate(@RequestParam String template) {
        return ResponseEntity.ok(lectorService.getNamesByTemplate(template));
    }

    @PutMapping("/{lectorId}")
    public ResponseEntity<Lector> updateLector(@PathVariable Long lectorId,
                                               @RequestBody LectorCredentials credentials) {
        Lector updatedLector = lectorService.updateLector(lectorId, credentials);
        return ResponseEntity.ok(updatedLector);
    }

    @DeleteMapping("/{lectorId}")
    public ResponseEntity<Void> deleteLector(@PathVariable Long lectorId) {
        lectorService.deleteLector(lectorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
