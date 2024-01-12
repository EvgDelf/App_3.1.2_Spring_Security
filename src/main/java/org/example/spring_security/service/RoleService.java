package org.example.spring_security.service;

import org.example.spring_security.model.Role;
import org.example.spring_security.model.User;
import org.example.spring_security.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void addRoleToUser(User user, String roleName) {
        Role role = roleRepository.findByName(roleName);
        roleRepository.save(role);
        user.getRoles().add(role);
    }

    public void removeRoleFromUser(User user, String roleName) {
        Role role = roleRepository.findByName(roleName);
        if (role != null) {
            user.getRoles().remove(role);
        }
    }

    public Role findByName (String name) {
        return roleRepository.findByName(name);
    }

}
