package com.indrachrsm.ewallet.customer;


import com.indrachrsm.ewallet.Convertable;
import com.indrachrsm.ewallet.wallet.WalletResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRespDto implements Convertable {
    private Integer id;
    private String name;
    private WalletResponseDto wallet;
}
