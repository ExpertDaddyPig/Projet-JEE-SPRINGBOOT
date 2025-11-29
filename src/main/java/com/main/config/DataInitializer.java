package com.main.config;

import com.main.model.Employe;
import com.main.repository.EmployeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(EmployeRepository employeRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (employeRepository.count() == 0) {
                Employe admin = new Employe();
                admin.setFirst_name("System");
                admin.setLast_name("Admin");
                admin.setGender("Other");
                admin.setRegistration_number("A1000");
                admin.setProjects("All Projects"); 
                admin.setJob_name("Administrator");
                admin.setEmploye_rank(4);
                admin.setAge(40);
                admin.setEmail("admin@admin.com");
                admin.setUsername("sys_admin");
                admin.setPassword_hash(passwordEncoder.encode("12345678"));
                admin.setCreatedAt(LocalDate.now());
                admin.setLastLogin(LocalDate.now());
                admin.setActive(true);
                // admin.setDepartement_id(1); 
                employeRepository.save(admin);
            }
        };
    }
}