package com.technicalchallenge.mapper;

import com.technicalchallenge.dto.CashflowDTO;
import com.technicalchallenge.model.Cashflow;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CashflowMapper {
    @Autowired
    private ModelMapper modelMapper;

    public CashflowDTO toDto(Cashflow entity) {
        return modelMapper.map(entity, CashflowDTO.class);
    }

    public Cashflow toEntity(CashflowDTO dto) {
        return modelMapper.map(dto, Cashflow.class);
    }
}
