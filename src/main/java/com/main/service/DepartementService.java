package com.main.service;

import com.main.model.Departement;
import com.main.repository.DepartementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DepartementService {

    private final DepartementRepository departementRepository;

    public DepartementService(DepartementRepository departementRepository) {
        this.departementRepository = departementRepository;
    }

    public List<Departement> findAll() {
        return departementRepository.findAll();
    }

    public Optional<Departement> findById(int id) {
        return departementRepository.findById(id);
    }

    public Departement save(Departement departement) {
        return departementRepository.save(departement);
    }

    public void deleteById(int id) {
        departementRepository.deleteById(id);
    }
}