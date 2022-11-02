package com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Role;
import com.repository.RoleRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class RoleControllerTest {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    private MvcResult mvcResult;
    private String uri = "/api/roles";
    
    @Test
    public void getAllRoles() throws Exception {
        uri += "/all";
        MvcResult mvcResult1 = this.mockMvc
                .perform(get(uri)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        Role[] roles = objectMapper.readValue(mvcResult1.getResponse().getContentAsString(), Role[].class);
        assertEquals(roleRepository.count(), roles.length);
    }

    @Test
    public void getRole() throws Exception {
        uri += "/1";
        MvcResult mvcResult1 = this.mockMvc
                .perform(get(uri)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        Role role = objectMapper.readValue(mvcResult1.getResponse().getContentAsString(), Role.class);
        assertEquals(roleRepository.findById(1L).get(), role);
    }

    @Test
    public void addRole() throws Exception {
        uri += "/new";
        Role role = new Role("guest");
        MvcResult mvcResult1 = this.mockMvc
                .perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(role)))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        assertTrue(roleRepository.findByName("guest").isPresent());
    }

    @Test
    public void updateRole() throws Exception {
        uri += "/1";
        Role role = new Role("visitor");

        MvcResult mvcResult1 = this.mockMvc
                .perform(put(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(role)))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        Role updatedRole = roleRepository.findById(1L).get();
        assertTrue(Objects.equals(updatedRole.getName(), role.getName()));
    }

    @Test
    public void deleteRole() throws Exception {
        assertTrue(roleRepository.findByName("guest").isPresent());

        MvcResult mvcResult1 = this.mockMvc
                .perform(delete(uri + "/3"))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        assertTrue(roleRepository.findById(3L).stream().findAny().isEmpty());
    }
}