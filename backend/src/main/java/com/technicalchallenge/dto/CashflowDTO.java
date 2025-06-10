package com.technicalchallenge.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CashflowDTO {
    private Long id;
    private BigDecimal paymentValue;
    private LocalDate valueDate;
    private String payRec;
    private String paymentType;
    private String paymentBusinessDayConvention;
    private Double rate;
}
