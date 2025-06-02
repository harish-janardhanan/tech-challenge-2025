package com.technicalchallenge.mapper;

import com.technicalchallenge.dto.TradeDTO;
import com.technicalchallenge.model.Trade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TradeMapper {
    @Autowired
    private ModelMapper modelMapper;

    public TradeDTO toDto(Trade entity) {
        return modelMapper.map(entity, TradeDTO.class);
    }

    public Trade toEntity(TradeDTO dto) {
        return modelMapper.map(dto, Trade.class);
    }
}
