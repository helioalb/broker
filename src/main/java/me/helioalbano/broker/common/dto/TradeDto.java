package me.helioalbano.broker.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.helioalbano.broker.common.enums.Asset;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradeDto implements Serializable {
    private Asset assetTraded;
    private Asset assetUsedToPay;
    private String buyer;
    private String seller;
    private BigDecimal quantity;
    private BigDecimal amount;
}
