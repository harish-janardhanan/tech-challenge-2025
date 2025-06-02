package com.technicalchallenge.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "trade_leg")
public class TradeLeg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long legId;
    private BigDecimal notional;
    private Double rate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_id", referencedColumnName = "id")
    private Trade trade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    private Currency currency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leg_rate_type_id", referencedColumnName = "id")
    private LegType legRateType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "index_id", referencedColumnName = "id")
    private Index index;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "holiday_calendar_id", referencedColumnName = "id")
    private HolidayCalendar holidayCalendar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calculation_period_schedule_id", referencedColumnName = "id")
    private Schedule calculationPeriodSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_business_day_convention_id", referencedColumnName = "id")
    private BusinessDayConvention paymentBusinessDayConvention;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fixing_business_day_convention_id", referencedColumnName = "id")
    private BusinessDayConvention fixingBusinessDayConvention;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pay_receive_flag_id", referencedColumnName = "id")
    private PayRec payReceiveFlag;
}
