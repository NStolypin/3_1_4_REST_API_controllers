package ru.esplit.first_security_app.services;

import java.util.List;
import java.util.Optional;

import ru.esplit.first_security_app.dto.RoleDTO;
import ru.esplit.first_security_app.models.Role;

public interface RoleService {

    List<Role> getAllRoles();

    Optional<Role> findById(String id);

    void takeBackTheRole(long id, String role_id);

    void giveTheRole(long id, String role_id);

    Optional<Role> convertToRole(RoleDTO roleDTO);
}
