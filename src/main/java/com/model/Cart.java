package com.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "cart")
public class Cart implements Serializable {
    private static final long SERIAL_VERSION_UID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
    
    @ElementCollection(targetClass=Book.class, fetch = FetchType.EAGER)
    private Set<Book> bookSet;

    public Cart() {
    }

    public Cart(long id, User user, Set<Book> bookSet) {
        this.id = id;
        this.user = user;
        this.bookSet = bookSet;
    }

    public Cart(User user, Set<Book> bookSet) {
        this.user = user;
        this.bookSet = bookSet;
    }

    public Cart(Set<Book> bookSet) {
        this.bookSet = bookSet;
    }

    public Cart(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Book> getBookSet() {
        return bookSet;
    }

    public void setBookSet(Set<Book> bookSet) {
        this.bookSet = bookSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return id == cart.id && Objects.equals(user, cart.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
