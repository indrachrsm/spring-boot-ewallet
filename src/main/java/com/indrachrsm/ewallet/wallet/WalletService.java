package com.indrachrsm.ewallet.wallet;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;

    public Wallet fetchById(Integer walletId) {
        return walletRepository.findById(walletId).orElseThrow(WalletNotFoundException::new);
    }

}
