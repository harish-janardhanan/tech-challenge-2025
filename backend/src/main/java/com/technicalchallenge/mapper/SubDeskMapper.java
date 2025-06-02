package com.technicalchallenge.mapper;

import com.technicalchallenge.dto.SubDeskDTO;
import com.technicalchallenge.model.SubDesk;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubDeskMapper {
    @Autowired
    private ModelMapper modelMapper;

    public SubDeskDTO toDto(SubDesk entity) {
        return modelMapper.map(entity, SubDeskDTO.class);
    }

    public SubDesk toEntity(SubDeskDTO dto) {
        return modelMapper.map(dto, SubDesk.class);
    }
}
