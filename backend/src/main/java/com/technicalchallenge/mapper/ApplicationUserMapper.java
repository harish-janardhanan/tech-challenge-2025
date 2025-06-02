package com.technicalchallenge.mapper;

import com.technicalchallenge.dto.UserDTO;
import com.technicalchallenge.model.ApplicationUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationUserMapper {
    @Autowired
    private ModelMapper modelMapper;

    public UserDTO toDto(ApplicationUser entity) {
        return modelMapper.map(entity, UserDTO.class);
    }

    public ApplicationUser toEntity(UserDTO dto) {
        return modelMapper.map(dto, ApplicationUser.class);
    }
}
