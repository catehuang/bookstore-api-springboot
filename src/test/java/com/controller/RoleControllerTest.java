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
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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
    private final String URI= "/api/roles";
    
    @Test
    public void getAllRoles() throws Exception {
        MvcResult mvcResult1 = this.mockMvc
                .perform(get(URI + "/all")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        Role[] roles = objectMapper.readValue(mvcResult1.getResponse().getContentAsString(), Role[].class);
        assertEquals(roleRepository.count(), roles.length);
    }

    @Test
    public void getRole() throws Exception {
        MvcResult mvcResult1 = this.mockMvc
                .perform(get(URI + "/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        Role role = objectMapper.readValue(mvcResult1.getResponse().getContentAsString(), Role.class);
        assertEquals(roleRepository.findById(1L).orElse(null), role);
    }

    @Test
    public void addRole() throws Exception {
        Role role = new Role("guest");
        MvcResult mvcResult1 = this.mockMvc
                .perform(post(URI + "/new")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(role)))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        assertTrue(roleRepository.findByName("guest").isPresent());
    }

    @Test
    public void updateRole() throws Exception {
        Role role = new Role("visitor");

        MvcResult mvcResult1 = this.mockMvc
                .perform(put(URI + "/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(role)))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        Role updatedRole = roleRepository.findById(1L).orElse(null);
        if (updatedRole == null) {
            throw new IllegalArgumentException("updated role is empty");
        }
        assertEquals(updatedRole.getName(), role.getName());
    }

    @Test
    public void deleteRole() throws Exception {
        assertTrue(roleRepository.findByName("guest").isPresent());

        MvcResult mvcResult1 = this.mockMvc
                .perform(delete(URI + "/3"))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        assertTrue(roleRepository.findById(3L).stream().findAny().isEmpty());
    }
}