package com.indrachrsm.ewallet.transaction;


import com.indrachrsm.ewallet.Convertable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequestDto implements Convertable {
    private Double amount;
    private String description;
    private Type type;
}
