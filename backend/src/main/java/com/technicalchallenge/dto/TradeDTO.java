package com.technicalchallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TradeDTO {
    private Long id;
    private Long tradeId;
    private Integer version;
    private BookDTO book;
    private CounterpartyDTO counterparty;
    private UserDTO traderUser;
    private UserDTO inputterUser;
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
}
