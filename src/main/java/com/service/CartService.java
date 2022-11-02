package com.service;

import com.model.Cart;
import com.model.User;
import com.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public Set<Cart> getAllCarts() {
        return new HashSet<Cart>(cartRepository.findAll());
    }

    public Cart addCart(Cart cart) {
        try {
            return cartRepository.save(cart);
        } catch(DataIntegrityViolationException e) {
            return null;
        }
    }

    public Cart update(long id, Cart cart) {
        cart.setId(id);
        return cartRepository.save(cart);
    }

    public void deleteCart(long id) {
        cartRepository.deleteById(id);
    }

    public Cart getCart(long id) {
        return cartRepository.findById(id).orElse(null);
    }

    public Cart getCartByUserId(long userId) {
        return cartRepository.findByUserId(userId).orElse(null);
    }
}
