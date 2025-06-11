package com.technicalchallenge.controller;

import com.technicalchallenge.dto.BookDTO;
import com.technicalchallenge.mapper.BookMapper;
import com.technicalchallenge.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.technicalchallenge.service.BookService;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @MockitoBean
    private BookMapper bookMapper;

    @BeforeEach
    public void setup() {
        Book book = new Book();
        book.setBookName("Book Name");
        book.setId(1L);
        book.setActive(true);
        book.setVersion(1);
        book.setCostCenter(null);


        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookName("Book Name");
        bookDTO.setId(1L);
        bookDTO.setActive(true);
        bookDTO.setVersion(1);

        when(bookService.getAllBooks()).thenReturn(List.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDTO);
        when(bookMapper.toEntity(bookDTO)).thenReturn(book);

    }

    @Test
    void shouldReturnAllBooks() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk());
    }
    // Add more tests for POST, PUT, DELETE as needed
}
