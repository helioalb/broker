package br.com.mercadolivre.broker.common;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class ErrorResponse {
    private LocalDateTime timestamp;
    private int code;
    private String message;
}
