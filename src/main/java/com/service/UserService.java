package com.service;

import com.model.User;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(long id) {
        return userRepository.findById(id);
    }

    public void addUser(User user) {
        user.setRole(roleRepository.findByName("user"));
        userRepository.save(user);
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
