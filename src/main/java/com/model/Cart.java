package com.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "cart")
public class Cart implements Serializable {
    private static final long SERIAL_VERSION_UID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "id") //insert a foreign key owner_id corresponding to owner's id
    private User owner;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "book_set",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> bookList;

    public Cart() {
    }

    public Cart(long id, User owner, List<Book> bookList) {
        this.id = id;
        this.owner = owner;
        this.bookList = bookList;
    }

    public Cart(User owner, List<Book> bookList) {
        this.owner = owner;
        this.bookList = bookList;
    }

    public Cart(List<Book> bookList) {
        this.bookList = bookList;
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
}
