package com.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "order")
public class Order implements Serializable {
    private static final long SERIAL_VERSION_UID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "payment_id")
    private String paymentId;

    @Column(name = "amount")
    private double amount;

    @Column(name = "created")
    private String created;

    @Column(name = "address")
    private String address;

    @Column(name = "payment_status")
    private String paymentStatus;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Book> bookSet;

    public Order() {
    }

    public Order(User user, Set<Book> bookSet, String paymentId, double amount, String created,
                 String address, String paymentStatus) {
        this.user = user;
        this.bookSet = bookSet;
        this.paymentId = paymentId;
        this.amount = amount;
        this.created = created;
        this.address = address;
        this.paymentStatus = paymentStatus;
    }

    public Order(long id, User user, Set<Book> bookSet, String paymentId, double amount,
                 String created, String address, String paymentStatus) {
        this.id = id;
        this.user = user;
        this.bookSet = bookSet;
        this.paymentId = paymentId;
        this.amount = amount;
        this.created = created;
        this.address = address;
        this.paymentStatus = paymentStatus;
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

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
