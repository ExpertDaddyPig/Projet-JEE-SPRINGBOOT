package com.main.controller;

import com.main.model.Employe;
import com.main.model.Payslip;
import com.main.service.EmployeService;
import com.main.service.PayslipService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/payslips")
public class PayslipController {

    private final PayslipService payslipService;
    private final EmployeService employeService;

    public PayslipController(PayslipService payslipService, EmployeService employeService) {
        this.payslipService = payslipService;
        this.employeService = employeService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("payslips", payslipService.findAll());
        return "payslips";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) Integer employe_id,
                         @RequestParam(required = false) Integer month,
                         Model model) {
        List<Payslip> results = payslipService.findAll();
        if (employe_id != null) {
            results = results.stream().filter(p -> p.getEmploye_id() == employe_id).collect(Collectors.toList());
            model.addAttribute("searchEmployeId", employe_id);
        }
        if (month != null) {
            results = results.stream().filter(p -> p.getMonth() == month).collect(Collectors.toList());
            model.addAttribute("searchMonth", month);
        }
        model.addAttribute("payslips", results);
        return "payslips";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("employees", employeService.findAll());
        return "payslip-create"; 
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Payslip payslip) {
        payslipService.save(payslip);
        return "redirect:/payslips?success=create";
    }

    @GetMapping("/view")
    public String view(@RequestParam int id, Model model) {
        Payslip payslip = payslipService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Id"));
        Employe employe = employeService.findById(payslip.getEmploye_id()).orElse(new Employe());
        
        model.addAttribute("payslip", payslip);
        model.addAttribute("employee", employe); 
        return "payslip-view";
    }

    @GetMapping("/print")
    public String print(@RequestParam int id, Model model) {
        Payslip payslip = payslipService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Id"));
        Employe employe = employeService.findById(payslip.getEmploye_id()).orElse(new Employe());
        
        model.addAttribute("payslip", payslip);
        model.addAttribute("employee", employe);
        return "payslip-print";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int id) {
        payslipService.deleteById(id);
        return "redirect:/payslips?success=delete";
    }
    
    @GetMapping("/employee")
    public String byEmployee(@RequestParam("id") int employeId, Model model) {
        List<Payslip> results = payslipService.findByEmployeId(employeId);
        model.addAttribute("payslips", results);
        model.addAttribute("searchEmployeId", employeId);
        return "payslips";
    }
}