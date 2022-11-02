package com.service;

import com.model.Book;
import com.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBook(long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public void update(long id, Book book) {
        book.setId(id);
        bookRepository.save(book);
    }

    public void deleteBook(long id) {
        try {
            bookRepository.deleteById(id);
        }
        catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

    }
}
