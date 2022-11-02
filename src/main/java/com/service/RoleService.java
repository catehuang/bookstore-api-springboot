package com.service;

import com.model.Role;
import com.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRole(long id) {
        return roleRepository.findById(id).orElse(null);
    }

    public void addRole(Role role) {
        roleRepository.save(role);
    }

    public void update(long id, Role role) {
        role.setId(id);
        roleRepository.save(role);
    }

    public void deleteRole(long id) {
        try {
            roleRepository.deleteById(id);
        }
        catch(Exception e) {
            System.out.println(e.getStackTrace());
        }

    }
}
