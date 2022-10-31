package com.controller;

import com.model.Cart;
import com.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("")
    public List<Cart> getAllCarts() {
        return cartService.getAllCarts();
    }

    @GetMapping("/{id}")
    public Optional<Cart> getCart(@PathVariable String id) {
        return cartService.getCart(Long.parseLong(id));
    }

    @PostMapping("")
    public void addCart(@RequestBody Cart cart) {
        cartService.addCart(cart);
    }

    @PutMapping("/{id}")
    public void updateCart(@RequestBody Cart cart, @PathVariable String id) {
        cartService.update(Long.parseLong(id), cart);
    }
    @DeleteMapping("/{id}")
    public void deleteCart(@PathVariable String id) {
        cartService.deleteCart(Long.parseLong(id));
    }
}
