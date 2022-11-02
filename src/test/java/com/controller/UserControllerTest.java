package com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Role;
import com.model.User;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    private MvcResult mvcResult;
    private final String URI = "/api";

    @Test
    public void getAllUsers() throws Exception {
        MvcResult mvcResult1 = this.mockMvc
                .perform(get(URI + "/users/all")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        assertEquals(200, mvcResult1.getResponse().getStatus());
        User[] users = objectMapper.readValue(mvcResult1.getResponse().getContentAsString(), User[].class);
        assertEquals(userRepository.count(), users.length);
    }

    @Test
    public void getUser() throws Exception {
        MvcResult mvcResult1 = this.mockMvc
                .perform(get(URI + "/users/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        User user = objectMapper.readValue(mvcResult1.getResponse().getContentAsString(), User.class);
        assertEquals(userRepository.findById(1L).orElse(null), user);


        long nextIndex = userRepository.findAll().size() + 1;
        assertTrue(userRepository.findById(nextIndex).isEmpty());
        mvcResult1 = this.mockMvc
                .perform(get(URI + "/users/" + nextIndex)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        assertEquals(200, mvcResult1.getResponse().getStatus());
        assertTrue(mvcResult1.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void login_pass() throws Exception {
        User user = new User("test", "password");
        MvcResult mvcResult1 = this.mockMvc
                .perform(post(URI + "/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        User returnUser = objectMapper.readValue(mvcResult1.getResponse().getContentAsString(), User.class);
        assertTrue(returnUser.getId() > 0);
    }

    @Test
    public void login_fail_1() throws Exception {
        User user = new User("test", "password123");
        MvcResult mvcResult1 = this.mockMvc
                .perform(post(URI + "/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        assertTrue(mvcResult1.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void login_fail_2() throws Exception {
        User user = new User("test123", "password");
        MvcResult mvcResult1 = this.mockMvc
                .perform(post(URI + "/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        assertTrue(mvcResult1.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void addUser() throws Exception {
        User user = new User("guest", "guest@example.com", "password");
        MvcResult mvcResult1 = this.mockMvc
                .perform(post(URI + "/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        assertTrue(userRepository.findByUsername("guest").getId() > 0);
    }

    @Test
    public void updateUser() throws Exception {
        User user = userRepository.findById(1L).orElse(null);
        if (user == null){
            throw  new IllegalArgumentException("user doesn't exist");
        }
        user.setEmail("new@example.com");
        user.setPassword("notpassowrd");
        user.setRole(roleRepository.findByName("admin").orElse(null));
        user.setUsername("new");

        MvcResult mvcResult1 = this.mockMvc
                .perform(put(URI + "/users/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        User updatedUser= userRepository.findById(1L).orElse(null);
        if (updatedUser == null) {
            throw new IllegalArgumentException("updated user is empty");
        }
        assertTrue(Objects.equals(updatedUser.getPassword(), user.getPassword()) &&
                Objects.equals(updatedUser.getEmail(), user.getEmail()) &&
                Objects.equals(updatedUser.getRole(), user.getRole()) &&
                Objects.equals(updatedUser.getUsername(), user.getUsername())
                );
    }

    @Test
    public void deleteUser() throws Exception {
        User user = userRepository.findByUsername("guest");
        if (user == null) {
            throw new IllegalArgumentException("user doesn't exist");
        }
        long userId = user.getId();;

        MvcResult mvcResult1 = this.mockMvc
                .perform(delete(URI + "/users/" + userId))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        assertTrue(userRepository.findById(userId).stream().findAny().isEmpty());
    }
}