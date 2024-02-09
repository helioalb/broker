package me.helioalbano.broker.wallet.infra.controller.dto;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import lombok.Data;

@Data
public class BalanceDto {
    private Set<Map<String, BigDecimal>> balances;
}
