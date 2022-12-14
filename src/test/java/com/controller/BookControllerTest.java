package com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Book;
import com.repository.BookRepository;
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
    private final String URI = "/api/books";

//    private String mapToJson(Object obj) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.writeValueAsString(obj);
//    }
//    private <T> T mapFromJson(String json, Class<T> clazz)
//            throws JsonParseException, JsonMappingException, IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.readValue(json, clazz);
//    }

    @Test
    public void getAllBooks() throws Exception {
        MvcResult mvcResult1 = this.mockMvc
                .perform(get(URI + "/all")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        Book[] books = objectMapper.readValue(mvcResult1.getResponse().getContentAsString(), Book[].class);
        assertEquals(bookRepository.count(), books.length);
    }

    @Test
    public void getBook() throws Exception {
        MvcResult mvcResult1 = this.mockMvc
                .perform(get(URI + "/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        Book book = objectMapper.readValue(mvcResult1.getResponse().getContentAsString(), Book.class);
        assertEquals(bookRepository.findById(1L).orElse(null), book);


        long nextIndex = bookRepository.findAll().size() + 1;
        assertTrue(bookRepository.findById(nextIndex).isEmpty());
        mvcResult1 = this.mockMvc
                .perform(get(URI + "/" + nextIndex)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        assertEquals(200, mvcResult1.getResponse().getStatus());
        assertTrue(mvcResult1.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void addBook() throws Exception {
        Book book = new Book("Test Book", "Book Author", "Book Image", 3.3, 1234,
                "Book Description", 14.56, 22);
        MvcResult mvcResult1 = this.mockMvc
                .perform(post(URI + "/new")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(book)))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        assertTrue(bookRepository.findByName("Test Book").stream().count() > 0);
    }

    @Test
    public void updateBook() throws Exception {
        Book book = new Book("Test Book", "Book Author", "Book Image", 3.3, 1234,
                "Book Description", 14.56, 22);

        MvcResult mvcResult1 = this.mockMvc
                .perform(put(URI + "/1")
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
        MvcResult mvcResult1 = this.mockMvc
                .perform(delete(URI + "/3"))
                .andReturn();

        assertEquals(200, mvcResult1.getResponse().getStatus());
        assertTrue(bookRepository.findById(3L).stream().findAny().isEmpty());
    }
}