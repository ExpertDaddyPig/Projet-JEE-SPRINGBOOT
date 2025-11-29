package com.main.controller;

import com.main.model.Departement;
import com.main.service.DepartementService;
import com.main.service.EmployeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/departments")
public class DepartementController {

    private final DepartementService departementService;
    private final EmployeService employeService;

    public DepartementController(DepartementService departementService, EmployeService employeService) {
        this.departementService = departementService;
        this.employeService = employeService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("departments", departementService.findAll());
        model.addAttribute("allEmployees", employeService.findAll());
        return "departments";
    }

    @PostMapping("/add")
    public String add(@RequestParam String name, @RequestParam(required = false) List<Integer> employeeIds) {
        Departement dept = new Departement();
        dept.setDepartement_name(name);
        if (employeeIds != null) {
            dept.setEmployees(employeeIds.stream().map(String::valueOf).collect(Collectors.joining(",")));
        }
        dept.setEmployeesCount();
        departementService.save(dept);
        return "redirect:/departments?success=add";
    }

    @PostMapping("/edit")
    public String edit(@RequestParam int id, @RequestParam String name, @RequestParam(required = false) List<Integer> employeeIds) {
        Departement dept = departementService.findById(id).orElse(new Departement());
        dept.setDepartement_name(name);
        if (employeeIds != null) {
            dept.setEmployees(employeeIds.stream().map(String::valueOf).collect(Collectors.joining(",")));
        } else {
            dept.setEmployees("");
        }
        dept.setEmployeesCount();
        departementService.save(dept);
        return "redirect:/departments?success=update";
    }
    
    @PostMapping("/delete")
    public String delete(@RequestParam int id) {
        departementService.deleteById(id);
        return "redirect:/departments?success=delete";
    }
}