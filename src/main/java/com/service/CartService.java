package com.service;

import com.model.Cart;
import com.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Optional<Cart> getCart(long id) {
        return cartRepository.findById(id);
    }

    public void addCart(Cart cart) {
        cartRepository.save(cart);
    }

    public void update(long id, Cart cart) {
        cart.setId(id);
        cartRepository.save(cart);
    }

    public void deleteCart(long id) {
        cartRepository.deleteById(id);
    }
}
