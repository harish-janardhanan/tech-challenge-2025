package com.technicalchallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SubDeskDTO {
    private Long id;
    private String subdeskName;
    private DeskDTO desk;
}
