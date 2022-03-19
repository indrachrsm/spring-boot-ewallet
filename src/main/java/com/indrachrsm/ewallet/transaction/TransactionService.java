package com.indrachrsm.ewallet.transaction;

import java.util.List;

import com.indrachrsm.ewallet.wallet.Wallet;
import com.indrachrsm.ewallet.wallet.WalletService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final WalletService walletService;

    public Transaction add(Integer walletId, Transaction transaction) {
        Wallet wallet = walletService.fetchById(walletId);
        Double amountToUpdate = transaction.convertAmount();
        wallet.updateBalance(amountToUpdate);
        transaction.setWallet(wallet);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> fetchByWalletId(Integer walletId) {
        Wallet wallet = walletService.fetchById(walletId);
        return transactionRepository.findAllByWallet(wallet);
    }

}
