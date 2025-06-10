package com.technicalchallenge.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TradeDTO {
    private Long id;
    private Long tradeId;
    private Integer version;
    private String bookName;
    private String counterpartyName;
    private String traderUserName;
    private String inputterUserName;
    private String tradeType;
    private String tradeSubType;
    private String tradeStatus;
    private String utiCode;
    private LocalDateTime tradeDate;
    private LocalDateTime startDate;
    private LocalDateTime maturityDate;
    private LocalDateTime executionDate;
    private Long additionalFieldsId;
    private LocalDateTime lastTouchTimestamp;
    private List<TradeLegDTO> tradeLegs;
    private LocalDateTime validityStartDate;
    private LocalDateTime validityEndDate;
}

