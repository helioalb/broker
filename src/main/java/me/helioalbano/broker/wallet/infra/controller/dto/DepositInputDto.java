package me.helioalbano.broker.wallet.infra.controller.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;
import me.helioalbano.broker.common.enums.Asset;

@Data
public class DepositInputDto {
    @NotNull(message = "asset must be sent")
    private Asset asset;

    @Min(0)
    @NotNull(message = "amount must be sent")
    private BigDecimal amount;
}
