package ru.esplit.first_security_app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.esplit.first_security_app.dto.RoleDTO;
import ru.esplit.first_security_app.models.Role;
import ru.esplit.first_security_app.repositories.RoleRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findById(String id) {
        return roleRepository.findById(id);
    }

    @Override
    public Optional<Role> convertToRole(RoleDTO roleDTO) {
        return findById(roleDTO.getId());
    }

}
