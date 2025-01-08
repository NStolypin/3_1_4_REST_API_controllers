package ru.esplit.first_security_app.controllers;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;
import ru.esplit.first_security_app.models.Person;
import ru.esplit.first_security_app.security.PersonDetails;
import ru.esplit.first_security_app.services.AdminService;
import ru.esplit.first_security_app.services.RoleService;

@Controller
@RequestMapping("/admin")
public class AdminsController {

    private final AdminService adminService;
    private final RoleService roleService;

    public AdminsController(AdminService adminService,
            RoleService roleService) {
        this.adminService = adminService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getAdminPage(@ModelAttribute("person") Person person, Model model) {
        PersonDetails personDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        model.addAttribute("personDetails", personDetails.getPerson());
        model.addAttribute("people", adminService.getAllPeople());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admins/hello";
    }
}
