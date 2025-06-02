package com.technicalchallenge.mapper;

import com.technicalchallenge.dto.BookDTO;
import com.technicalchallenge.model.Book;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    @Autowired
    private ModelMapper modelMapper;

    public BookDTO toDto(Book entity) {
        return modelMapper.map(entity, BookDTO.class);
    }

    public Book toEntity(BookDTO dto) {
        return modelMapper.map(dto, Book.class);
    }
}
