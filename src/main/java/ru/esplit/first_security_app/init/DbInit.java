package ru.esplit.first_security_app.init;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import ru.esplit.first_security_app.models.Person;
import ru.esplit.first_security_app.models.Role;
import ru.esplit.first_security_app.repositories.RoleRepository;
import ru.esplit.first_security_app.services.RegistrationService;

@Component
@RequiredArgsConstructor
public class DbInit {

    private final RegistrationService registrationService;
    private final RoleRepository roleRepository;

    @PostConstruct
    private void postConstruct() {
        Role userRole = new Role("ROLE_USER");
        Role adminRole = new Role("ROLE_ADMIN");
        roleRepository.save(userRole);
        roleRepository.save(adminRole);

        Person admin = new Person();
        admin.setUsername("admin");
        admin.setYearOfBirth(2000);
        admin.setPassword("admin");
        List<Role> rolesForAdmin = new ArrayList<>(List.of(adminRole, userRole));
        admin.setRoles(rolesForAdmin);
        registrationService.registerUser(admin);

        Person user = new Person();
        user.setUsername("user");
        user.setYearOfBirth(2000);
        user.setPassword("auser");
        List<Role> rolesForUser = Collections.singletonList(userRole);
        user.setRoles(rolesForUser);
        registrationService.registerUser(user);
    }
}
