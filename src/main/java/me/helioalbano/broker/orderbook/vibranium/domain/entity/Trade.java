package me.helioalbano.broker.orderbook.vibranium.domain.entity;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.helioalbano.broker.common.enums.Asset;

@AllArgsConstructor
@Getter
public class Trade {
    private Asset asset;
    private Intent bid;
    private Intent ask;
    private BigDecimal quantity;
    private BigDecimal amount;
}
