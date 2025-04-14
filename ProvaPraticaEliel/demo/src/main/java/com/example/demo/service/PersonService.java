package com.example.demo.service;

import com.example.demo.dto.PersonDTO;
import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.WorkRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final WorkRepository workRepository;

    public PersonService(PersonRepository personRepository, WorkRepository workRepository) {
        this.personRepository = personRepository;
        this.workRepository = workRepository;
    }

    public PersonDTO createPerson(PersonDTO personDTO) {
        if (personDTO == null || personDTO.getName() == null || personDTO.getEmail() == null) {
            throw new IllegalArgumentException("PessoaDTO e seus campos não pode ser nulo");
        }

        if (personDTO.getWorkId() != null) {
            workRepository.findById(personDTO.getWorkId())
                    .orElseThrow(() -> new RuntimeException("Nenhum trabalho achado com o ID: " + personDTO.getWorkId()));
        }

        Person person = new Person(personDTO.getName(), personDTO.getEmail(), personDTO.getWorkId());
        person = personRepository.save(person);
        return new PersonDTO(person.getId(), person.getName(), person.getEmail(), person.getWorkId());
    }

    public List<PersonDTO> getAllPersons() {
        return personRepository.findAll().stream()
                .map(person -> new PersonDTO(person.getId(), person.getName(), person.getEmail(), person.getWorkId()))
                .collect(Collectors.toList());
    }

    public PersonDTO getPersonById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }

        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nenhuma pessoa achada com o ID: " + id));
        return new PersonDTO(person.getId(), person.getName(), person.getEmail(), person.getWorkId());
    }

    public PersonDTO updatePerson(Long id, PersonDTO personDTO) {
        if (id == null || personDTO == null) {
            throw new IllegalArgumentException("ID e Pessoa DTO não pode ser nulo");
        }

        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nenhuma pessoa achada com o ID: " + id));
        person.setName(personDTO.getName());
        person.setEmail(personDTO.getEmail());

        if (personDTO.getWorkId() != null) {
            workRepository.findById(personDTO.getWorkId())
                    .orElseThrow(() -> new RuntimeException("Nenhum trabalho achado com o ID: " + personDTO.getWorkId()));
        }
        person.setWorkId(personDTO.getWorkId());
        person = personRepository.save(person);
        return new PersonDTO(person.getId(), person.getName(), person.getEmail(), person.getWorkId());
    }

    public void deletePerson(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        personRepository.deleteById(id);
    }
}