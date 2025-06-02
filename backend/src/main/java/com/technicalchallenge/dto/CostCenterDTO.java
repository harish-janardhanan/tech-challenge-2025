package com.technicalchallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CostCenterDTO {
    private Long id;
    private String costCenterName;
    private SubDeskDTO subDesk;
}
