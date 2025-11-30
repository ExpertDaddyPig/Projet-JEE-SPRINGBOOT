package com.main.config;

import com.main.model.Departement;
import com.main.model.Employe;
import com.main.repository.DepartementRepository;
import com.main.repository.EmployeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(EmployeRepository employeRepository, DepartementRepository departementRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (employeRepository.count() == 0) {
                Employe admin = new Employe();
                admin.setFirstName("System");
                admin.setLastName("Admin");
                admin.setGender("Other");
                admin.setRegistrationNumber("A1000");
                admin.setProjects("All Projects"); 
                admin.setJobName("Administrator");
                admin.setEmployeRank(4);
                admin.setAge(40);
                admin.setEmail("admin@admin.com");
                admin.setUsername("sys_admin");
                admin.setPasswordHash(passwordEncoder.encode("12345678"));
                admin.setCreatedAt(LocalDateTime.now());
                admin.setLastLogin(LocalDateTime.now());
                admin.setActive(true);
                admin.setDepartement_id(1);
                employeRepository.save(admin);
            }

            if (departementRepository.count() == 0) {
                Departement administration = new Departement();
                administration.setEmployees("0");
                administration.setEmployeesCount();
                administration.setDepartement_name("Administration");
                departementRepository.save(administration);
            }
        };
    }
}