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

    @GetMapping("/users")
    public String showAllUsers(Model model) {
        PersonDetails personDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        model.addAttribute("personDetails", personDetails.getPerson());
        model.addAttribute("people", adminService.getAllPeople());
        return "admins/get_all_users";
    }

    @GetMapping("/users/{id}")
    public String showOneUser(@PathVariable("id") long id, Model model) {
        PersonDetails personDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        model.addAttribute("personDetails", personDetails.getPerson());
        Optional<Person> person = adminService.getOnePerson(id);
        if (person.isPresent()) {
            model.addAttribute("person", person.get());
            model.addAttribute("roles", roleService.getAllRoles());
            return "admins/edit";
        }
        return "redirect:/admin";
    }

    @GetMapping("/users/{id}/edit")
    public String editUser(Model model, @PathVariable("id") long id) {
        model.addAttribute("person", adminService.getOnePerson(id));
        return "admins/edit";
    }

    @PostMapping("/users/{id}")
    public String updateUser(@ModelAttribute("person") @Valid Person person,
            BindingResult bindingResult, @PathVariable("id") long id, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.getAllRoles());
            return "admins/edit";
        }
        adminService.updateUser(id, person);
        return "redirect:/admin";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        PersonDetails personDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        model.addAttribute("personDetails", personDetails.getPerson());
        adminService.deleteUser(id, personDetails.getPerson().getId());
        return "redirect:/admin";
    }

    @GetMapping("/users/new")
    public String createNewUser(@ModelAttribute("person") Person person, Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        return "admins/new";
    }

    @PostMapping("/users/new")
    public String createNewUser(@ModelAttribute("person") @Valid Person person,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.getAllRoles());
            return "admins/new";
        }
        adminService.createUser(person);
        return "redirect:/admin";
    }

    @GetMapping("/users/{id}/editrole")
    public String editRole(Model model, @PathVariable("id") long id) {
        PersonDetails personDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        model.addAttribute("personDetails", personDetails.getPerson());
        model.addAttribute("person", adminService.getOnePerson(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "admins/get_all_roles";
    }

    @PostMapping("/users/{id}/addrole/{role_id}")
    public String giveTheRole(@PathVariable("id") long id, @PathVariable("role_id") String role_id) {
        roleService.giveTheRole(id, role_id);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/deleterole/{role_id}")
    public String takeBackTheRole(@PathVariable("id") long id, @PathVariable("role_id") String role_id) {
        roleService.takeBackTheRole(id, role_id);
        return "redirect:/admin/users";
    }
}
