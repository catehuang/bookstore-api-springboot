package com.repository;

import com.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Set<Book> findByName(String name);
}
