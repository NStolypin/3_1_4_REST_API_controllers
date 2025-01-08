package ru.esplit.first_security_app.services;

import java.util.List;
import java.util.Optional;

import ru.esplit.first_security_app.dto.RoleDTO;
import ru.esplit.first_security_app.models.Role;

public interface RoleService {

    List<Role> getAllRoles();

    Optional<Role> findById(String id);

    Optional<Role> convertToRole(RoleDTO roleDTO);
}
