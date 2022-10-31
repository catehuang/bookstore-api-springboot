package com.service;

import com.model.Order;
import com.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrder(long id) {
        return orderRepository.findById(id);
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
}
