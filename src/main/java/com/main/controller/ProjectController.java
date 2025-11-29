package com.main.controller;

import com.main.model.Employe;
import com.main.model.Project;
import com.main.service.EmployeService;
import com.main.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final EmployeService employeService;

    public ProjectController(ProjectService projectService, EmployeService employeService) {
        this.projectService = projectService;
        this.employeService = employeService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) String state, Model model) {
        List<Project> projects = projectService.findAll();
        if (state != null && !state.isEmpty()) {
            projects = projects.stream()
                    .filter(p -> p.getProject_state().equalsIgnoreCase(state))
                    .collect(Collectors.toList());
            model.addAttribute("stateFilter", state);
        }
        model.addAttribute("projects", projects);
        return "projects";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("project", new Project());
        return "project-create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Project project) {
        if (project.getProject_state() == null) {
            project.setProject_state("in process");
        }
        projectService.save(project);
        return "redirect:/projects?success=create";
    }

    @GetMapping("/view")
    public String view(@RequestParam int id, Model model) {
        Project project = projectService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Id"));
        model.addAttribute("project", project);

        List<Employe> assignedEmployees = new ArrayList<>();
        if (project.getEmployees() != null && !project.getEmployees().isEmpty()) {
            List<Integer> ids = Arrays.stream(project.getEmployees().split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            
            for (Integer empId : ids) {
                employeService.findById(empId).ifPresent(assignedEmployees::add);
            }
        }
        model.addAttribute("assignedEmployees", assignedEmployees);
        return "project-view";
    }

    @GetMapping("/edit")
    public String editForm(@RequestParam int id, Model model) {
        Project project = projectService.findById(id).orElseThrow();
        model.addAttribute("project", project);
        return "project-edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Project project) {
        Project existing = projectService.findById(project.getId()).orElseThrow();
        project.setEmployees(existing.getEmployees());
        projectService.save(project);
        return "redirect:/projects/view?id=" + project.getId() + "&success=update";
    }

    @GetMapping("/assign")
    public String assignForm(@RequestParam int id, Model model) {
        Project project = projectService.findById(id).orElseThrow();
        model.addAttribute("project", project);
        model.addAttribute("allEmployees", employeService.findAll());

        List<Integer> currentEmployees = new ArrayList<>();
        if (project.getEmployees() != null && !project.getEmployees().isEmpty()) {
            currentEmployees = Arrays.stream(project.getEmployees().split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        }
        model.addAttribute("currentEmployees", currentEmployees);
        return "project-assign";
    }

    @PostMapping("/assign")
    public String assign(@RequestParam int id, @RequestParam(value = "employee_ids", required = false) List<Integer> employeeIds) {
        Project project = projectService.findById(id).orElseThrow();
        if (employeeIds != null && !employeeIds.isEmpty()) {
            String empString = employeeIds.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            project.setEmployees(empString);
        } else {
            project.setEmployees("");
        }
        projectService.save(project);
        return "redirect:/projects/view?id=" + id + "&success=assign";
    }

    @PostMapping("/changeState")
    public String changeState(@RequestParam int id, @RequestParam String state) {
        Project project = projectService.findById(id).orElseThrow();
        project.setProject_state(state);
        projectService.save(project);
        return "redirect:/projects/view?id=" + id + "&success=stateChanged";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int id) {
        projectService.deleteById(id);
        return "redirect:/projects?success=delete";
    }
}