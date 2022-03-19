package com.indrachrsm.ewallet.wallet;

import com.indrachrsm.ewallet.Convertable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletResponseDto implements Convertable {
    private Integer id;
    private Integer balance;
}
