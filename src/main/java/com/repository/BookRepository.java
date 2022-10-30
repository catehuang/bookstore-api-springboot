package com.repository;

import com.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
