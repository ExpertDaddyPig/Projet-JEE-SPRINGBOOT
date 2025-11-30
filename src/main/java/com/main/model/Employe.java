package com.main.model;

import java.time.LocalDateTime;
import java.util.Random;
import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(name = "gender", length = 50, nullable = false)
    private String gender;

    @Column(name = "registration_number", length = 10, nullable = false)
    private String registrationNumber;

    @Column(name = "departement_id", nullable = true)
    private Integer departementId;

    @Column(name = "projects", length = 200, nullable = true)
    private String projects;

    @Column(name = "job_name", length = 100, nullable = true)
    private String jobName;

    @Column(name = "employe_rank", nullable = false)
    private Integer employeRank;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "username", length = 50, nullable = false)
    private String username;

    @Transient
    private String password;

    @Column(name = "password_hash", length = 255, nullable = false)
    private String passwordHash;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Transient
    private Role role;

    public Employe() {
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
    }

    public Employe(String first_name, String last_name, String email, String gender, int employe_rank, int age) {
        this.firstName = first_name;
        this.lastName = last_name;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.employeRank = employe_rank;
        this.registrationNumber = generateRegistrationNumber();
    }

    public Employe(String first_name, String last_name, String email, String gender, String job_name,
            int departement_id,
            int employe_rank, int age) {
        this(first_name, last_name, email, gender, employe_rank, age);
        this.jobName = job_name;
        this.departementId = departement_id;
    }

    public Employe(String first_name, String last_name, String email, String gender, String job_name, int projects_id[],
            int departement_id, int employe_rank, int age) {
        this(first_name, last_name, email, gender, employe_rank, age);
        this.jobName = job_name;
        this.departementId = departement_id;
        this.employeRank = employe_rank;
        StringBuilder projectsIdStrings = new StringBuilder();
        if (projects_id != null) {
            for (int project_id : projects_id) {
                projectsIdStrings.append(project_id);
                projectsIdStrings.append("|");
            }
        }
        this.projects = projectsIdStrings.toString();
    }

    private String generateRegistrationNumber() {
        String candidateChars = "1234567890";
        StringBuilder generated = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            generated.append(candidateChars.charAt(random.nextInt(candidateChars.length())));
        }
        return generated.toString();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String first_name) {
        this.firstName = first_name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String last_name) {
        this.lastName = last_name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Integer getEmployeRank() {
        return employeRank;
    }

    public void setEmployeRank(Integer employe_rank) {
        this.employeRank = employe_rank;
        this.role = null;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String job_name) {
        this.jobName = job_name;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registration_number) {
        this.registrationNumber = registration_number;
    }

    public void initRegistrationNumber() {
        setRegistrationNumber(generateRegistrationNumber());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String password) {
        this.passwordHash = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        if (role == null && employeRank != null) {
            switch (employeRank) {
                case 2:
                    setRole(Role.CHEF_PROJET);
                    break;
                case 3:
                    setRole(Role.CHEF_DEPARTEMENT);
                    break;
                case 4:
                    setRole(Role.ADMINISTRATEUR);
                    break;
                default:
                    setRole(Role.EMPLOYE);
                    break;
            }
        }
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setDepartement_id(int departement_id) {
        this.departementId = departement_id;
    }

    public Integer getDepartementId() {
        return departementId;
    }

    public String getProjects() {
        return projects;
    }

    public void setProjects(Project[] projects) {
        StringBuilder projectList = new StringBuilder();
        if (projects != null) {
            for (Project project : projects) {
                projectList.append(project.getId()).append(',');
            }
        }
        this.projects = projectList.toString();
    }

    public void setProjects(String projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "Employe{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role=" + getRole() +
                '}';
    }
}