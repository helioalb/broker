package br.com.mercadolivre.broker.orderbook.vibranium.domain.entity;

import java.math.BigDecimal;

import br.com.mercadolivre.broker.wallet.domain.enums.Asset;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Trade {
    private Asset asset;
    private Intent bid;
    private Intent ask;
    private BigDecimal quantity;
    private BigDecimal price;
}
