package com.technicalchallenge.mapper;

import com.technicalchallenge.dto.CostCenterDTO;
import com.technicalchallenge.model.CostCenter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CostCenterMapper {
    @Autowired
    private ModelMapper modelMapper;

    public CostCenterDTO toDto(CostCenter entity) {
        return modelMapper.map(entity, CostCenterDTO.class);
    }

    public CostCenter toEntity(CostCenterDTO dto) {
        return modelMapper.map(dto, CostCenter.class);
    }
}
