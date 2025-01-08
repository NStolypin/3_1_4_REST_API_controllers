package ru.esplit.first_security_app.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import ru.esplit.first_security_app.models.Person;
import ru.esplit.first_security_app.security.PersonDetails;
import ru.esplit.first_security_app.services.RoleService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminsController {

    private final RoleService roleService;

    @GetMapping
    public String getAdminPage(@ModelAttribute("person") Person person, Model model) {
        PersonDetails personDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        model.addAttribute("personDetails", personDetails.getPerson());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admins/hello";
    }
}
