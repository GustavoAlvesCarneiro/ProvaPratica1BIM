package com.example.demo.service;

import com.example.demo.dto.WorkDTO;
import com.example.demo.model.Work;
import com.example.demo.repository.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkService {
    @Autowired
    private WorkRepository workRepository;

    public WorkDTO createWork(WorkDTO workDTO) {
        Work work = new Work(workDTO.getTitle(), workDTO.getDescription());
        work = workRepository.save(work);
        return new WorkDTO(work.getId(), work.getTitle(), work.getDescription());
    }

    public List<WorkDTO> getAllWorks() {
        return workRepository.findAll().stream()
                .map(work -> new WorkDTO(work.getId(), work.getTitle(), work.getDescription()))
                .collect(Collectors.toList());
    }

    public WorkDTO getWorkById(Long id) {
        Work work = workRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nenhum trabalho achado"));
        return new WorkDTO(work.getId(), work.getTitle(), work.getDescription());
    }

    public WorkDTO updateWork(Long id, WorkDTO workDTO) {
        Work work = workRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nenhum trabalho achado"));
        work.setTitle(workDTO.getTitle());
        work.setDescription(workDTO.getDescription());
        work = workRepository.save(work);
        return new WorkDTO(work.getId(), work.getTitle(), work.getDescription());
    }

    public void deleteWork(Long id) {
        workRepository.deleteById(id);
    }
}