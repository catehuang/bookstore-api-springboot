package com.service;

import com.model.Order;
import com.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Set<Order> getAllOrders() {
        return new HashSet<Order>(orderRepository.findAll());
    }

    public Order getOrder(long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void addOrder(Order order) {
        orderRepository.save(order);
    }

    public void update(long id, Order order) {
        order.setId(id);
        orderRepository.save(order);
    }

    public void deleteOrder(long id) {
        orderRepository.deleteById(id);
    }

    public Set<Order> getOrdersByUserId(long userId) {
        return new HashSet<Order>(orderRepository.findAllByUserId(userId));
    }
}
