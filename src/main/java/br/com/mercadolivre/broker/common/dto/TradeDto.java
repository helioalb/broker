package br.com.mercadolivre.broker.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import br.com.mercadolivre.broker.common.enums.Asset;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
