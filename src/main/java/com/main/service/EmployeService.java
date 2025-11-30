package com.main.service;

import com.main.model.Employe;
import com.main.repository.EmployeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeService {

    private final EmployeRepository employeRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeService(EmployeRepository employeRepository, PasswordEncoder passwordEncoder) {
        this.employeRepository = employeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Employe> findAll() {
        return employeRepository.findAll();
    }

    public Optional<Employe> findById(int id) {
        return employeRepository.findById(id);
    }

    public Optional<Employe> findByUsername(String username) {
        return employeRepository.findByUsername(username);
    }

    public Employe save(Employe employe) {
        if (employe.getPassword() != null && !employe.getPassword().isEmpty()) {
            employe.setPasswordHash(passwordEncoder.encode(employe.getPassword()));
        }
        return employeRepository.save(employe);
    }

    public void deleteById(int id) {
        employeRepository.deleteById(id);
    }
}