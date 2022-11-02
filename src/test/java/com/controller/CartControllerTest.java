package com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Book;
import com.model.Cart;
import com.model.User;
import com.repository.BookRepository;
import com.repository.CartRepository;
import com.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class CartControllerTest {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    private MvcResult mvcResult;
    private String uri = "/api/carts";

    @Test
    public void getAllCarts() throws Exception {
        uri += "/all";
        MvcResult mvcResult1 = this.mockMvc
                .perform(get(uri)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        Cart[] carts = objectMapper.readValue(mvcResult1.getResponse().getContentAsString(), Cart[].class);
        assertEquals(cartRepository.count(), carts.length);
    }

    @Test
    public void getCart() throws Exception {
        uri += "/1";
        MvcResult mvcResult1 = this.mockMvc
                .perform(get(uri)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        Cart cart = objectMapper.readValue(mvcResult1.getResponse().getContentAsString(), Cart.class);
        assertTrue(cartRepository.findById(1L).get().getUser().getId() == cart.getUser().getId());
    }

    @Test
    public void getCartByUserId() throws Exception {
        uri += "/find";
        MvcResult mvcResult1 = this.mockMvc
                .perform(get(uri + "/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        Cart cart = objectMapper.readValue(mvcResult1.getResponse().getContentAsString(), Cart.class);
        assertTrue(cartRepository.findByUserId(1L).get().getId() == cart.getId());

        mvcResult1 = this.mockMvc
                .perform(get(uri + "/2")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        assertTrue(mvcResult1.getResponse().getContentAsString().isEmpty());
        assertTrue(cartRepository.findByUserId(2L).isEmpty());
    }

    @Test
    public void addCartByUser_1() throws Exception {
        uri += "/new";

        // Test case: cart and user is one-to-one
        User user = userRepository.findById(1L).get();

        MvcResult mvcResult1 = this.mockMvc
                .perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andReturn();
        assertEquals(200, mvcResult1.getResponse().getStatus());
        assertTrue(cartRepository.findByUserId(1L).stream().count() == 1);
    }

    @Test
    public void addCartByUser_2() throws Exception {
        // Test case: add a new cart
        uri += "/new";
        User user = userRepository.findById(2L).get();
        assertTrue(cartRepository.findByUserId(2L).isEmpty());

        MvcResult mvcResult1 = this.mockMvc
                .perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        assertTrue(cartRepository.findByUserId(2L).isPresent());
    }

    @Test
    public void updateCart() throws Exception {
        uri += "/1";
        Book book = bookRepository.findById(2L).get();
        List<Book> books = new ArrayList<>();
        books.add(bookRepository.findById(2L).get());
        User user = userRepository.findById(1L).get();
        Cart cart = new Cart(user, books);

        MvcResult mvcResult1 = this.mockMvc
                .perform(put(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(cart)))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        assertTrue(cartRepository.findByUserId(1L).get().getBookList().get(0).getId() == 2L);
    }

    @Test
    public void deleteCart() throws Exception {
        uri += "/1";
        MvcResult mvcResult1 = this.mockMvc
                .perform(delete(uri))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        assertTrue(cartRepository.findById(1L).stream().findAny().isEmpty());
    }
}