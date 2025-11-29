package com.main.config;

import com.main.model.Employe;
import com.main.service.EmployeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final EmployeService employeService;

    public GlobalControllerAdvice(EmployeService employeService) {
        this.employeService = employeService;
    }

    @ModelAttribute
    public void addUserToSession(HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            if (session.getAttribute("currentUser") == null) {
                String username = auth.getName();
                employeService.findByUsername(username).ifPresent(employe -> {
                    session.setAttribute("currentUser", employe);
                });
            }
        }
    }
}