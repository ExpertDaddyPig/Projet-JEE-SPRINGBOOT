package com.main.controller;

import com.main.model.Employe;
import com.main.model.Role;
import com.main.service.DepartementService;
import com.main.service.EmployeService;
import com.main.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class EmployeController {

    private final EmployeService employeService;
    private final DepartementService departementService;
    private final ProjectService projectService;

    public EmployeController(EmployeService employeService, DepartementService departementService, ProjectService projectService) {
        this.employeService = employeService;
        this.departementService = departementService;
        this.projectService = projectService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) String search, Model model) {
        model.addAttribute("users", employeService.findAll()); 
        model.addAttribute("departments", departementService.findAll());
        model.addAttribute("roles", Role.values());
        model.addAttribute("projectList", projectService.findAll());
        
        return "employees";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Employe employe, @RequestParam("departement") Integer departementId, @RequestParam(value = "projects", required = false) int[] projectIds) {
        employe.setEmployeRank(employe.getRole().getLevel());
        employe.setDepartement_id(departementId);
        employe.initRegistrationNumber();
        if (projectIds != null) {
            StringBuilder sb = new StringBuilder();
            for (int id : projectIds) sb.append(id).append("|");
        }
        try {
            employeService.save(employe);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/users";
        }
        return "redirect:/users?success=add";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Employe employe) {
        employeService.save(employe);
        return "redirect:/users?success=update";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        employeService.deleteById(id);
        return "redirect:/users?success=delete";
    }
}