package com.technicalchallenge.mapper;

import com.technicalchallenge.dto.TradeLegDTO;
import com.technicalchallenge.model.TradeLeg;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TradeLegMapper {
    @Autowired
    private ModelMapper modelMapper;

    public TradeLegDTO toDto(TradeLeg entity) {
        return modelMapper.map(entity, TradeLegDTO.class);
    }

    public TradeLeg toEntity(TradeLegDTO dto) {
        return modelMapper.map(dto, TradeLeg.class);
    }
}
