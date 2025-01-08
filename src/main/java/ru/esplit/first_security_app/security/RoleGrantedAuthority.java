package ru.esplit.first_security_app.security;

import org.springframework.security.core.GrantedAuthority;

import lombok.RequiredArgsConstructor;
import ru.esplit.first_security_app.models.Role;

@RequiredArgsConstructor
public class RoleGrantedAuthority implements GrantedAuthority {

    private final Role role;
    
    @Override
    public String getAuthority() {
        return this.role.getId();
    }
    
}
