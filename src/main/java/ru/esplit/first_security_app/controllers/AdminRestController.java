package ru.esplit.first_security_app.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ru.esplit.first_security_app.dto.IdPersonDTO;
import ru.esplit.first_security_app.dto.PersonDTO;
import ru.esplit.first_security_app.security.PersonDetails;
import ru.esplit.first_security_app.services.AdminService;
import ru.esplit.first_security_app.services.PeopleService;
import ru.esplit.first_security_app.services.RoleService;
import ru.esplit.first_security_app.util.PersonDoNotDeleteException;
import ru.esplit.first_security_app.util.PersonErrorResponse;
import ru.esplit.first_security_app.util.PersonNotCreatedException;
import ru.esplit.first_security_app.util.PersonNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class AdminRestController {

    private final AdminService adminService;
    private final PeopleService peopleService;

    public AdminRestController(AdminService adminService,
            RoleService roleService,
            PeopleService peopleService) {
        this.adminService = adminService;
        this.peopleService = peopleService;
    }

    @GetMapping("/allpeople")
    public List<PersonDTO> showAllUsers(Model model) {
        PersonDTO personDTO = new PersonDTO();
        return personDTO.getPersonDTOList(adminService.getAllPeople());
    }

    @GetMapping("/person/{id}")
    public PersonDTO getPerson(@PathVariable("id") long id) {
        PersonDTO personDTO = new PersonDTO();
        return personDTO.getPersonDTO(peopleService.findOne(id));
    }

    @PostMapping("/person")
    public ResponseEntity<?> create(@RequestBody @Valid PersonDTO personDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }

            throw new PersonNotCreatedException(errorMsg.toString());
        }
        IdPersonDTO idPersonDTO = new IdPersonDTO();
        idPersonDTO.setId(peopleService.saveUser(peopleService.convertToPerson(personDTO, false)));
        return new ResponseEntity<>(idPersonDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> delete(@RequestBody IdPersonDTO idPersonDTO) {
        PersonDetails personDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if (idPersonDTO.getId() == personDetails.getPerson().getId()) {
            String errorMsg = "Вы не можете удалить сами себя";
            throw new PersonDoNotDeleteException(errorMsg);
        }
        adminService.deleteUser(idPersonDTO.getId(), personDetails.getPerson().getId());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/person")
    public ResponseEntity<HttpStatus> patch(@RequestBody @Valid PersonDTO personDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }

            throw new PersonNotCreatedException(errorMsg.toString());
        }
        peopleService.saveUser(peopleService.convertToPerson(personDTO, true));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                "Person with this id wasn't found",
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                e.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
