package com.indrachrsm.ewallet.wallet;


import com.indrachrsm.ewallet.factories.WalletFactory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WalletTest {
    
    @Test
    void updateBalance_shouldUpdateWalletBalance() {
        double amount = 1000.0;
        Wallet wallet = WalletFactory.get().build();

        wallet.updateBalance(amount);

        Assertions.assertEquals(amount, wallet.getBalance());
    }

    @Test
    void updateBalance_shouldThrowInsufficientBalanceException_whenUpdatedBalanceBelow0() {
        Wallet wallet = WalletFactory.get().build();

        Assertions.assertThrows(InsufficientBalanceException.class, () -> wallet.updateBalance(-1000.0));
    }
}
