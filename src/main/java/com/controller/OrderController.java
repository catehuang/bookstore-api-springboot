package com.controller;

import com.model.Order;
import com.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Optional<Order> getOrder(@PathVariable String id) {
        return orderService.getOrder(Long.parseLong(id));
    }

    @PostMapping("")
    public void addOrder(@RequestBody Order order) {
        orderService.addOrder(order);
    }

    @PutMapping("/{id}")
    public void updateOrder(@RequestBody Order order, @PathVariable String id) {
        orderService.update(Long.parseLong(id), order);
    }
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(Long.parseLong(id));
    }
}
