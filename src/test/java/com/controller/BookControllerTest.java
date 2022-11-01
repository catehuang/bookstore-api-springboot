package com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Book;
import com.repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    private MvcResult mvcResult;
    private String uri = "/api/books";

//    private String mapToJson(Object obj) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.writeValueAsString(obj);
//    }
//    private <T> T mapFromJson(String json, Class<T> clazz)
//            throws JsonParseException, JsonMappingException, IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.readValue(json, clazz);
//    }

    @BeforeEach
    public void setUp() {
        mvcResult = null;
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void getAllBooks() throws Exception {
        uri += "/all";
        MvcResult mvcResult1 = this.mockMvc
                .perform(get(uri)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        Book[] books = objectMapper.readValue(mvcResult1.getResponse().getContentAsString(), Book[].class);
        assertEquals(bookRepository.count(), books.length);
    }

    @Test
    public void getBook() throws Exception {
        uri += "/1";
        MvcResult mvcResult1 = this.mockMvc
                .perform(get(uri)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        Book book = objectMapper.readValue(mvcResult1.getResponse().getContentAsString(), Book.class);
        assertEquals(bookRepository.findById(1L).get(), book);
    }

    @Test
    public void addBook() throws Exception {
        uri += "/new";
        Book book = new Book("Test Book", "Book Author", "Book Image", 3.3, 1234,
                "Book Description", 14.56, 22);
        MvcResult mvcResult1 = this.mockMvc
                .perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(book)))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        assertTrue(bookRepository.findByName("Test Book").stream().count() > 0);
        assertFalse(bookRepository.findByName("Test 123").stream().count() > 0);
    }

    @Test
    public void updateBook() throws Exception {
        uri += "/1";
        Book book = new Book("Test Book", "Book Author", "Book Image", 3.3, 1234,
                "Book Description", 14.56, 22);

        MvcResult mvcResult1 = this.mockMvc
                .perform(put(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(book)))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        Book updatedBook = bookRepository.findById(1L).get();
        assertTrue(Objects.equals(updatedBook.getName(), book.getName()) &&
                Objects.equals(updatedBook.getAuthor(), book.getAuthor()) &&
                Objects.equals(updatedBook.getImage(), book.getImage()) &&
                updatedBook.getStars() == book.getStars() &&
                updatedBook.getReviews() == book.getReviews() &&
                Objects.equals(updatedBook.getDescription(), book.getDescription()) &&
                updatedBook.getPrice() == book.getPrice() &&
                updatedBook.getQuantity() == book.getQuantity()
        );
    }

    @Test
    public void deleteBook() throws Exception {
        uri += "/1";
        MvcResult mvcResult1 = this.mockMvc
                .perform(delete(uri))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        assertTrue(bookRepository.findById(1L).stream().findAny().isEmpty());
    }
}