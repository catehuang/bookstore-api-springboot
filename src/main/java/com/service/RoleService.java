package com.service;

import com.model.Role;
import com.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> getRole(long id) {
        return roleRepository.findById(id);
    }

    public void addRole(Role role) {
        roleRepository.save(role);
    }

    public void update(long id, Role role) {
        role.setId(id);
        roleRepository.save(role);
    }

    public void deleteRole(long id) {
        roleRepository.deleteById(id);
    }
}
