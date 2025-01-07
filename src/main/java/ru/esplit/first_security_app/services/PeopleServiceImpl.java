package ru.esplit.first_security_app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.esplit.first_security_app.dto.PersonDTO;
import ru.esplit.first_security_app.dto.RoleDTO;
import ru.esplit.first_security_app.models.Person;
import ru.esplit.first_security_app.models.Role;
import ru.esplit.first_security_app.repositories.PeopleRepository;
import ru.esplit.first_security_app.util.PersonNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PeopleServiceImpl implements PeopleService {

    private final PeopleRepository peopleRepository;
    private final RegistrationService registrationService;
    private final RoleService roleService;

    @Override
    public Person findOne(long id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElseThrow(PersonNotFoundException::new);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public long saveUser(Person person) {
        return this.registrationService.registerUser(person);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public Person convertToPerson(PersonDTO personDTO, boolean isApplyId) {
        Person person = new Person();
        if (isApplyId) {
            person.setId(personDTO.getId());
        }
        person.setPassword(personDTO.getPassword());
        person.setUsername(personDTO.getUsername());
        person.setYearOfBirth(personDTO.getYearOfBirth());
        List<Role> roles = new ArrayList<>();
        for (RoleDTO roleDTO : personDTO.getRoles()) {
            Optional<Role> roleO = roleService.convertToRole(roleDTO);
            if (roleO.isPresent()) {
                roles.add(roleO.get());
            }
        }
        person.setRoles(roles);
        return person;
    }
}
