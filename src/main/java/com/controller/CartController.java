package com.controller;

import com.model.Cart;
import com.model.User;
import com.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/all")
    public Set<Cart> getAllCarts() {
        return cartService.getAllCarts();
    }

    @GetMapping("/find/{userId}")
    public Cart getCartByUserId(@PathVariable String userId) {
        return cartService.getCartByUserId(Long.parseLong(userId));
    }

    @GetMapping("/{cartId}")
    public Cart getCart(@PathVariable String cartId) {
        return cartService.getCart(Long.parseLong(cartId));
    }

    @PostMapping("/new")
    public Cart addCartByUser(@RequestBody User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartService.addCart(cart);
    }

    @PutMapping("/{id}")
    public Cart updateCart(@RequestBody Cart cart, @PathVariable String id) {
        return cartService.update(Long.parseLong(id), cart);
    }
    @DeleteMapping("/{id}")
    public void deleteCart(@PathVariable String id) {
        cartService.deleteCart(Long.parseLong(id));
    }
}
