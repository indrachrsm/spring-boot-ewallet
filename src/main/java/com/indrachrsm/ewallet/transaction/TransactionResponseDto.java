package com.indrachrsm.ewallet.transaction;

import java.time.LocalDateTime;

import com.indrachrsm.ewallet.Convertable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDto implements Convertable {
    private Integer id;
    private Double amount;
    private String description;
    private Type type;
    private LocalDateTime createdDateTime;
}
