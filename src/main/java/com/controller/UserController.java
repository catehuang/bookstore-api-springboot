package com.controller;

import com.model.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public Optional<User> getUser(@PathVariable String id) {
        return userService.getUser(Long.parseLong(id));
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        if (user.getUsername() != null && user.getPassword() != null) {
            return userService.login(user.getUsername(), user.getPassword());
        }
        else {
            return null;
        }
    }

    @PostMapping("/register")
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @PutMapping("/users/{id}")
    public void updateUser(@RequestBody User user, @PathVariable String id) {
        userService.update(Long.parseLong(id), user);
    }
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(Long.parseLong(id));
    }
}
