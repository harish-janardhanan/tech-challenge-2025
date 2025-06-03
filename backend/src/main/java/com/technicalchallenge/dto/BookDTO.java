package com.technicalchallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
public class BookDTO {
    private Long id;
    private String bookName;
    private boolean active;
    private int version;
    private CostCenterDTO costCenter;
}
