package com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Book;
import com.model.Order;
import com.model.User;
import com.repository.BookRepository;
import com.repository.OrderRepository;
import com.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    private MvcResult mvcResult;
    private final String URI = "/api/orders";

    @Test
    public void getAllOrders() throws Exception {
        MvcResult mvcResult1 = this.mockMvc
                .perform(get(URI + "/all")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        Order[] orders = objectMapper.readValue(mvcResult1.getResponse().getContentAsString(), Order[].class);
        assertEquals(orderRepository.count(), orders.length); // the number of orders is the same as the number of orders of return result
    }

    @Test
    public void getOrder() throws Exception {
        assertTrue(orderRepository.findById(1L).isPresent()); //check the order with id = 1L exists
        MvcResult mvcResult1 = this.mockMvc
                .perform(get(URI + "/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        Order order = objectMapper.readValue(mvcResult1.getResponse().getContentAsString(), Order.class);
        assertEquals(orderRepository.findById(1L).get(), order);

        long nextIndex = orderRepository.findAll().size() + 1;
        assertTrue(orderRepository.findById(nextIndex).isEmpty()); //a non-existed order number
        mvcResult1 = this.mockMvc
                .perform(get(URI + "/" + nextIndex)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        assertEquals(200, mvcResult1.getResponse().getStatus());
        assertTrue(mvcResult1.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void getOrdersByUserId() throws Exception {
        assertFalse(orderRepository.findAllByUserId(1L).isEmpty()); //check the user with id = 1 has order(s)
        MvcResult mvcResult1 = this.mockMvc
                .perform(get(URI + "/find/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        Order[] order = objectMapper.readValue(mvcResult1.getResponse().getContentAsString(), Order[].class);
        assertEquals((long) orderRepository.findAllByUserId(1).size(), order.length);

        long nextIndex = userRepository.findAll().size() + 1;
        assertTrue(orderRepository.findById(nextIndex).isEmpty()); //check there's no order with id = 5L
        mvcResult1 = this.mockMvc
                .perform(get(URI + "/" + nextIndex)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        assertEquals(200, mvcResult1.getResponse().getStatus());
        assertTrue(mvcResult1.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void addOrder() throws Exception {
        User user = userRepository.findById(1L).orElse(null);
        Book book = bookRepository.findById(1L).orElse(null);
        if (user == null || book == null) {
            throw new IllegalArgumentException("user or book is empty");
        }
        Set<Book> books = new HashSet<>();
        books.add(book);
        Order order = new Order(user, books, "0asfawq6wasf123", 11.60, "2020-12-24",
                "Ontario", "pending");
        MvcResult mvcResult1 = this.mockMvc
                .perform(post(URI + "/new")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(order)))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        assertEquals(2, orderRepository.findAllByUserId(1L).size());

        user = userRepository.findById(2L).orElse(null);
        book = bookRepository.findById(1L).orElse(null);
        if (user == null || book == null) {
            throw new IllegalArgumentException("user or book is empty");
        }
        books = new HashSet<>();
        books.add(book);
        order = new Order(user, books, "0asfawq6wasf123", 11.60, "2020-12-24", "Ontario", "pending");
        mvcResult1 = this.mockMvc
                .perform(post(URI + "/new")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(order)))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        assertEquals(1, orderRepository.findAllByUserId(2L).size());
    }

    @Test
    public void updateOrder() throws Exception {
        Order order = orderRepository.findById(1L).orElse(null);
        if (order == null) {
            throw new IllegalArgumentException("order is empty");
        }
        order.setPaymentStatus("Successful");
        order.setAddress("Earth");
        order.setAmount(123.45);
        order.setCreated("2022-11-22");
        Book book = bookRepository.findById(2L).orElse(null);
        Set<Book> bookSet = order.getBookSet();
        bookSet.add(book);
        order.setBookSet(bookSet);
        order.setPaymentId("9876543211");

        MvcResult mvcResult1 = this.mockMvc
                .perform(put(URI + "/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(order)))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        Order updatedOrder = orderRepository.findById(1L).orElse(null);
        if (updatedOrder == null) {
            throw new IllegalArgumentException("updated order is empty");
        }
        assertTrue(Objects.equals(updatedOrder.getAddress(), order.getAddress()) &&
                Objects.equals(updatedOrder.getPaymentStatus(), order.getPaymentStatus()) &&
                Objects.equals(updatedOrder.getAmount(), order.getAmount()) &&
                Objects.equals(updatedOrder.getCreated(), order.getCreated()) &&
                Objects.equals(updatedOrder.getPaymentId(), order.getPaymentId()) &&
                Objects.equals(updatedOrder.getBookSet(), order.getBookSet())
        );
    }

    @Test
    public void deleteOrder() throws Exception {
        Order order = orderRepository.findById(3L).orElse(null);
        if (order == null) {
            throw new IllegalArgumentException("order is empty");
        }
        MvcResult mvcResult1 = this.mockMvc
                .perform(delete(URI + "/3"))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        assertTrue(bookRepository.findById(3L).stream().findAny().isEmpty());
    }
}