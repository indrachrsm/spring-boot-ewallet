package com.indrachrsm.ewallet.wallet;

import java.util.Optional;

import com.indrachrsm.ewallet.factories.WalletFactory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {
    @Mock
    private WalletRepository walletRepository;
    @InjectMocks
    private WalletService walletService;

    @Test
    void fetchById_shouldReturnWallet_whenWalletExist() {
        int walletId = 1;
        Wallet wallet = WalletFactory.get().withId(walletId).build();
        Mockito.when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));

        Wallet actualResult = walletService.fetchById(walletId);

        Assertions.assertEquals(wallet, actualResult);
    }

    @Test
    void fetchById_shouldThrowWalletNotFoundException_whenWalletNotExist() {
        int walletId = 1;
        Mockito.when(walletRepository.findById(walletId)).thenReturn(Optional.empty());

        Assertions.assertThrows(WalletNotFoundException.class, () -> walletService.fetchById(walletId));
    }
}
