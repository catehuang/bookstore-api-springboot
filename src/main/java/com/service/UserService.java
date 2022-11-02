package com.service;

import com.model.User;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public Set<User> getAllUsers() {
        return new HashSet<User>(userRepository.findAll());
    }

    public User getUser(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User addUser(User user) {
        user.setRole(roleRepository.findByName("user").get());
        return userRepository.save(user);
    }

    public void update(long id, User user) {
        user.setId(id);
        userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public User login(String username, String password) {
        User foundUser = userRepository.findByUsername(username);
        if (foundUser != null && foundUser.getPassword().equals(password)) {
            return foundUser;
        }
        else {
            return null;
        }
    }
}
