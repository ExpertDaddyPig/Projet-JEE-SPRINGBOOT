package com.main.controller;

import com.main.model.Employe;
import com.main.model.Project;
import com.main.service.DepartementService;
import com.main.service.EmployeService;
import com.main.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reports")
public class ReportController {

    private final EmployeService employeService;
    private final DepartementService departementService;
    private final ProjectService projectService;

    public ReportController(EmployeService employeService, DepartementService departementService, ProjectService projectService) {
        this.employeService = employeService;
        this.departementService = departementService;
        this.projectService = projectService;
    }

    @GetMapping
    public String reports(Model model) {
        Map<String, Object> stats = new HashMap<>();
        List<Employe> employees = employeService.findAll();
        List<Project> projects = projectService.findAll();

        stats.put("totalEmployees", employees.size());
        stats.put("activeEmployees", employees.stream().filter(Employe::isActive).count());
        stats.put("totalDepartments", departementService.findAll().size());
        stats.put("totalProjects", projects.size());
        stats.put("activeProjects", projects.stream().filter(p -> "in process".equals(p.getProject_state())).count());
        stats.put("finishedProjects", projects.stream().filter(p -> "finished".equals(p.getProject_state())).count());

        Map<String, Long> byRank = employees.stream()
            .collect(Collectors.groupingBy(e -> e.getRole().getDisplayName(), Collectors.counting()));
        stats.put("employeesByRank", byRank);

        Map<String, Long> byDept = employees.stream()
            .filter(e -> e.getDepartement_id() != null)
            .collect(Collectors.groupingBy(e -> "Dept " + e.getDepartement_id(), Collectors.counting()));
        stats.put("employeesByDepartment", byDept);

        stats.put("employeesByProject", new HashMap<String, Long>());

        model.addAttribute("stats", stats);
        return "reports";
    }
}