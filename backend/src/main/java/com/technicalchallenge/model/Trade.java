package com.technicalchallenge.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "trade")
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long tradeId;
    private Integer version;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "counterparty_id", referencedColumnName = "id")
    private Counterparty counterparty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trader_user_id", referencedColumnName = "id")
    private ApplicationUser traderUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inputter_user_id", referencedColumnName = "id")
    private ApplicationUser inputterUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_type_id", referencedColumnName = "id")
    private TradeType tradeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_sub_type_id", referencedColumnName = "id")
    private TradeSubType tradeSubType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_status_id", referencedColumnName = "id")
    private TradeStatus tradeStatus;

    private String utiCode;
    private LocalDateTime tradeDate;
    private LocalDateTime startDate;
    private LocalDateTime maturityDate;
    private LocalDateTime executionDate;
    private Long additionalFieldsId;
    private LocalDateTime lastTouchTimestamp;
    private LocalDateTime validityStartDate;
    private LocalDateTime validityEndDate;

    @OneToMany(mappedBy = "trade", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TradeLeg> tradeLegs;
}
