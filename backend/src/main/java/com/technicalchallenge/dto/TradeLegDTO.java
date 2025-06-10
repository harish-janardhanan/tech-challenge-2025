package com.technicalchallenge.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TradeLegDTO {
    private Long legId;
    private BigDecimal notional;
    private String currency;
    private String legType;
    private String index;
    private String holidayCalendar;
    private String calculationPeriodSchedule;
    private String paymentBusinessDayConvention;
    private String fixingBusinessDayConvention;
    private String payReceiveFlag;
    private Double rate;
    private List<CashflowDTO> cashflows;

    // Note: calculationPeriodSchedule should be set using a managed Schedule entity from the database.
    // Ensure in your service/mapper that you look up the Schedule by name and set the managed entity, not a new instance.
}
