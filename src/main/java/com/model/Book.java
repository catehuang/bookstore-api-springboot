package com.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "book")
public class Book implements Serializable {

    private static final long SERIAL_VERSION_UID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String author;
    private String image;
    private double stars;
    private int reviews;
    @Column(length = 65555)
    private String description;
    private double price;
    private int quantity;

    public Book() {
    }

    public Book(long id, String name, String author, String image, double stars,
                int reviews, String description, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.image = image;
        this.stars = stars;
        this.reviews = reviews;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public Book(String name, String author, String image, double stars, int reviews, String description,
                double price, int quantity) {
        this.name = name;
        this.author = author;
        this.image = image;
        this.stars = stars;
        this.reviews = reviews;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        this.stars = stars;
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
