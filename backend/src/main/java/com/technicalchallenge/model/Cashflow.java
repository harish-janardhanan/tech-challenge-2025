package com.technicalchallenge.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cashflow")
public class Cashflow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal paymentValue;
    private LocalDate valueDate;
    private Double rate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leg_id", referencedColumnName = "legId")
    private TradeLeg leg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pay_rec_id", referencedColumnName = "id")
    private PayRec payRec;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_type_id", referencedColumnName = "id")
    private LegType paymentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_business_day_convention_id", referencedColumnName = "id")
    private BusinessDayConvention paymentBusinessDayConvention;
}
