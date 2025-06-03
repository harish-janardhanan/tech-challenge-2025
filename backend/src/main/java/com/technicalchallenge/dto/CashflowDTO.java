package com.technicalchallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Data
public class CashflowDTO {
    private Long id;
    private TradeLegDTO leg;
    private BigDecimal value;
    private LocalDate valueDate;
    private String payRec;
    private String paymentType;
    private String paymentBusinessDayConvention;
    private Double rate;
}
