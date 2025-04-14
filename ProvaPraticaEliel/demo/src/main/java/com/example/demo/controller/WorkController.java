package com.example.demo.controller;

import com.example.demo.dto.WorkDTO;
import com.example.demo.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/works")
public class WorkController {
    @Autowired
    private WorkService workService;

    @PostMapping
    public ResponseEntity<WorkDTO> createWork(@RequestBody WorkDTO workDTO) {
        return ResponseEntity.ok(workService.createWork(workDTO));
    }

    @GetMapping
    public ResponseEntity<List<WorkDTO>> getAllWorks() {
        return ResponseEntity.ok(workService.getAllWorks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkDTO> getWorkById(@PathVariable Long id) {
        return ResponseEntity.ok(workService.getWorkById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkDTO> updateWork(@PathVariable Long id, @RequestBody WorkDTO workDTO) {
        return ResponseEntity.ok(workService.updateWork(id, workDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWork(@PathVariable Long id) {
        workService.deleteWork(id);
        return ResponseEntity.noContent().build();
    }
}