package com.controller;

import com.model.Role;
import com.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/all")
    public Set<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/{id}")
    public Role getRole(@PathVariable String id) {
        return roleService.getRole(Long.parseLong(id));
    }

    @PostMapping("/new")
    public void addRole(@RequestBody Role role) {
        roleService.addRole(role);
    }

    @PutMapping("/{id}")
    public void updateRole(@RequestBody Role role, @PathVariable String id) {
        roleService.update(Long.parseLong(id), role);
    }
    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable String id) {
        roleService.deleteRole(Long.parseLong(id));
    }
}
