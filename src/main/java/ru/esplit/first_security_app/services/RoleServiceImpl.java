package ru.esplit.first_security_app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.esplit.first_security_app.dto.RoleDTO;
import ru.esplit.first_security_app.models.Person;
import ru.esplit.first_security_app.models.Role;
import ru.esplit.first_security_app.repositories.RoleRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final AdminService adminService;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findById(String id) {
        return roleRepository.findById(id);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void takeBackTheRole(long id, String role_id) {
        Optional<Role> roleO = roleRepository.findById(role_id);
        Optional<Person> personO = adminService.getOnePerson(id);
        if (personO.isPresent() & roleO.isPresent()) {
            if (personO.get().getRoles().contains(roleO.get())) {
                personO.get().getRoles().remove(roleO.get());
            }
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void giveTheRole(long id, String role_id) {
        Optional<Role> roleO = roleRepository.findById(role_id);
        Optional<Person> personO = adminService.getOnePerson(id);
        if (personO.isPresent() & roleO.isPresent()) {
            if (!personO.get().getRoles().contains(roleO.get())) {
                List<Role> lr = (List<Role>) personO.get().getRoles();
                lr.add(roleO.get());
            }
        }
    }

    @Override
    public Optional<Role> convertToRole(RoleDTO roleDTO) {
        return findById(roleDTO.getId());
    }

}
