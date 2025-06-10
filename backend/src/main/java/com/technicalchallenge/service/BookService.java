package com.technicalchallenge.service;

import com.technicalchallenge.dto.BookDTO;
import com.technicalchallenge.model.Book;
import com.technicalchallenge.repository.BookRepository;
import com.technicalchallenge.repository.CostCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CostCenterRepository costCenterRepository;

    public List<Book> getAllBooks() {
        logger.info("Retrieving all books");
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        logger.debug("Retrieving book by id: {}", id);
        return bookRepository.findById(id);
    }

    public void populateReferenceDataByName(Book book, BookDTO dto) {
        if (dto.getCostCenterName() != null && !dto.getCostCenterName().isBlank()) {
            var costCenter = costCenterRepository.findAll().stream()
                .filter(c -> c.getCostCenterName().equalsIgnoreCase(dto.getCostCenterName()))
                .findFirst().orElse(null);
            if (costCenter == null) throw new IllegalArgumentException("CostCenter '" + dto.getCostCenterName() + "' does not exist");
            book.setCostCenter(costCenter);
        }
        // If costCenterName is null or blank, do not modify the current costCenter
    }

    public Book saveBook(Book book, BookDTO dto) {
        logger.info("Saving book: {}", book);
        populateReferenceDataByName(book, dto);
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        logger.warn("Deleting book with id: {}", id);
        bookRepository.deleteById(id);
    }
}
