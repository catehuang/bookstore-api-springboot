package com.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "order")
public class Order implements Serializable {
    private static final long SERIAL_VERSION_UID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User owner;

    @Column(name = "payment")
    private String payment;

    @Column(name = "amount")
    private double amount;

    @Column(name = "created")
    private String created;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private String status;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "order_set",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> bookList;

    public Order() {
    }

    public Order(User owner, List<Book> bookList, String payment, double amount, String created,
                 String address, String status) {
        this.owner = owner;
        this.bookList = bookList;
        this.payment = payment;
        this.amount = amount;
        this.created = created;
        this.address = address;
        this.status = status;
    }

    public Order(long id, User owner, List<Book> bookList, String payment, double amount,
                 String created, String address, String status) {
        this.id = id;
        this.owner = owner;
        this.bookList = bookList;
        this.payment = payment;
        this.amount = amount;
        this.created = created;
        this.address = address;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public String getPayment_id() {
        return payment;
    }

    public void setPayment_id(String payment) {
        this.payment = payment;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
