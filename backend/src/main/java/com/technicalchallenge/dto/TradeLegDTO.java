package com.technicalchallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Data
public class TradeLegDTO {
    private Long legId;
    private TradeDTO trade;
    private BigDecimal notional;
    private String currency;
    private String legRateType;
    private String index;
    private String holidayCalendar;
    private String calculationPeriodSchedule;
    private String paymentBusinessDayConvention;
    private String fixingBusinessDayConvention;
    private String payReceiveFlag;
    private Double rate;
}
