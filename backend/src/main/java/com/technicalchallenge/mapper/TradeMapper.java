package com.technicalchallenge.mapper;

import com.technicalchallenge.dto.TradeDTO;
import com.technicalchallenge.model.Trade;
import com.technicalchallenge.repository.BookRepository;
import com.technicalchallenge.repository.CounterpartyRepository;
import com.technicalchallenge.repository.ApplicationUserRepository;
import com.technicalchallenge.model.Book;
import com.technicalchallenge.model.Counterparty;
import com.technicalchallenge.model.ApplicationUser;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.technicalchallenge.repository.TradeTypeRepository;
import com.technicalchallenge.repository.TradeSubTypeRepository;
import com.technicalchallenge.repository.TradeStatusRepository;

@Component
public class TradeMapper {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TradeLegMapper tradeLegMapper;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CounterpartyRepository counterpartyRepository;
    @Autowired
    private ApplicationUserRepository applicationUserRepository;
    @Autowired
    private TradeTypeRepository tradeTypeRepository;
    @Autowired
    private TradeSubTypeRepository tradeSubTypeRepository;
    @Autowired
    private TradeStatusRepository tradeStatusRepository;

    public TradeDTO toDto(Trade entity) {
        TradeDTO dto = new TradeDTO();
        // Map trade legs
        if (entity.getTradeLegs() != null) {
            dto.setTradeLegs(entity.getTradeLegs().stream()
                .map(tradeLegMapper::toDto)
                .collect(Collectors.toList()));
        }
        // Set string fields for book, counterparty, and users
        dto.setBookName(entity.getBook() != null ? entity.getBook().getBookName() : null);
        dto.setCounterpartyName(entity.getCounterparty() != null ? entity.getCounterparty().getName() : null);
        dto.setTraderUserName(entity.getTraderUser() != null ? entity.getTraderUser().getLoginId() : null);
        dto.setInputterUserName(entity.getInputterUser() != null ? entity.getInputterUser().getLoginId() : null);
        dto.setValidityStartDate(entity.getValidityStartDate());
        dto.setValidityEndDate(entity.getValidityEndDate());
        dto.setTradeStatus(entity.getTradeStatus() != null ? entity.getTradeStatus().getTradeStatus() : null);
        dto.setTradeType(entity.getTradeType() != null ? entity.getTradeType().getTradeType() : null);
        dto.setTradeSubType(entity.getTradeSubType() != null ? entity.getTradeSubType().getTradeSubType() : null);
        // Map other fields as needed
        dto.setId(entity.getId());
        dto.setTradeId(entity.getTradeId());
        dto.setVersion(entity.getVersion());
        dto.setUtiCode(entity.getUtiCode());
        dto.setTradeDate(entity.getTradeDate());
        dto.setStartDate(entity.getStartDate());
        dto.setMaturityDate(entity.getMaturityDate());
        dto.setExecutionDate(entity.getExecutionDate());
        dto.setAdditionalFieldsId(entity.getAdditionalFieldsId());
        dto.setLastTouchTimestamp(entity.getLastTouchTimestamp());
        return dto;
    }

    public Trade toEntity(TradeDTO dto) {
        Trade entity = modelMapper.map(dto, Trade.class);
        // Set version: 1 if null, otherwise increment by 1
        Integer currentVersion = dto.getVersion();
        entity.setVersion(currentVersion == null ? 1 : currentVersion + 1);
        // Look up and set Book
        if (dto.getBookName() != null) {
            Book book = bookRepository.findByBookName(dto.getBookName())
                .orElseThrow(() -> new IllegalArgumentException("Book not found: " + dto.getBookName()));
            entity.setBook(book);
        }
        // Look up and set Counterparty
        if (dto.getCounterpartyName() != null) {
            Counterparty counterparty = counterpartyRepository.findByName(dto.getCounterpartyName())
                .orElseThrow(() -> new IllegalArgumentException("Counterparty not found: " + dto.getCounterpartyName()));
            entity.setCounterparty(counterparty);
        }
        // Look up and set Trader User
        if (dto.getTraderUserName() != null) {
            ApplicationUser trader = applicationUserRepository.findByLoginId(dto.getTraderUserName())
                .orElseThrow(() -> new IllegalArgumentException("Trader user not found: " + dto.getTraderUserName()));
            entity.setTraderUser(trader);
        }
        // Look up and set Inputter User
        if (dto.getInputterUserName() != null) {
            ApplicationUser inputter = applicationUserRepository.findByLoginId(dto.getInputterUserName())
                .orElseThrow(() -> new IllegalArgumentException("Inputter user not found: " + dto.getInputterUserName()));
            entity.setInputterUser(inputter);
        }
        entity.setValidityStartDate(dto.getValidityStartDate());
        entity.setValidityEndDate(dto.getValidityEndDate());
        // Map TradeLegs using TradeLegMapper and set parent reference
        if (dto.getTradeLegs() != null) {
            var legs = dto.getTradeLegs().stream()
                .map(tradeLegMapper::toEntity)
                .collect(Collectors.toList());
            for (var leg : legs) {
                leg.setTrade(entity);
            }
            entity.setTradeLegs(legs);
        }

        // Set trade status from DTO if provided, otherwise default to LIVE
        if (dto.getTradeStatus() != null) {
            entity.setTradeStatus(tradeStatusRepository.findAll().stream()
                .filter(ts -> dto.getTradeStatus().equalsIgnoreCase(ts.getTradeStatus()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("TradeStatus not found: " + dto.getTradeStatus())));
        } else {
            entity.setTradeStatus(tradeStatusRepository.findAll().stream()
                .filter(ts -> "LIVE".equalsIgnoreCase(ts.getTradeStatus()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("TradeStatus 'LIVE' not found")));
        }

        // Use only trade type from DTO - no fallback
        if (dto.getTradeType() != null) {
            entity.setTradeType(tradeTypeRepository.findAll().stream()
                .filter(tt -> dto.getTradeType().equalsIgnoreCase(tt.getTradeType()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("TradeType not found: " + dto.getTradeType())));
        }

        // Use only trade sub type from DTO - no fallback
        if (dto.getTradeSubType() != null) {
            entity.setTradeSubType(tradeSubTypeRepository.findAll().stream()
                .filter(st -> dto.getTradeSubType().equalsIgnoreCase(st.getTradeSubType()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("TradeSubType not found: " + dto.getTradeSubType())));
        }
        return entity;
    }
}
