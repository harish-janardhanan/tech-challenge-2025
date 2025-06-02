package com.technicalchallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookDTO {
    private Long id;
    private String bookName;
    private boolean active;
    private int version;
    private CostCenterDTO costCenter;
}
