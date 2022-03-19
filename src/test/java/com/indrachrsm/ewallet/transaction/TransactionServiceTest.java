package com.indrachrsm.ewallet.transaction;

import java.util.Collections;
import java.util.List;

import com.indrachrsm.ewallet.factories.TransactionFactory;
import com.indrachrsm.ewallet.factories.WalletFactory;
import com.indrachrsm.ewallet.wallet.InsufficientBalanceException;
import com.indrachrsm.ewallet.wallet.Wallet;
import com.indrachrsm.ewallet.wallet.WalletNotFoundException;
import com.indrachrsm.ewallet.wallet.WalletService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
    @Mock
    private WalletService walletService;
    @Mock
    private TransactionRepository transactionRepository;
    @InjectMocks
    private TransactionService transactionService;

    @Test
    void add_shouldReturnSavedTransactionAndIncreasedWalletBalance_whenWalletIdExistAndTypeIsCredit() {
        int walletId = 1;
        double amount = 1000;
        Wallet wallet = WalletFactory.get().withId(walletId).build();
        double expectedBalance = wallet.getBalance() + amount;
        Transaction transaction = TransactionFactory.get().withAmount(amount).withWallet(wallet).build(Type.CREDIT);
        Mockito.when(walletService.fetchById(walletId)).thenReturn(wallet);
        Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);

        Transaction actualResult = transactionService.add(walletId, transaction);

        Assertions.assertEquals(transaction, actualResult);
        Assertions.assertEquals(expectedBalance, wallet.getBalance());
    }

    @Test
    void add_shouldReturnSavedTransactionAndDecreaseddWalletBalance_whenWalletIdExistAndTypeIsDebit() {
        int walletId = 1;
        double amount = 1000;
        Wallet wallet = WalletFactory.get().withId(walletId).withBalance(5000).build();
        double expectedBalance = wallet.getBalance() - amount;
        Transaction transaction = TransactionFactory.get().withAmount(amount).withWallet(wallet).build(Type.DEBIT);
        Mockito.when(walletService.fetchById(walletId)).thenReturn(wallet);
        Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);

        Transaction actualResult = transactionService.add(walletId, transaction);

        Assertions.assertEquals(transaction, actualResult);
        Assertions.assertEquals(expectedBalance, wallet.getBalance());
    }

    @Test
    void add_shouldWalletNotFoundException_whenWalletServiceFetchByIdThrowsWalletNotFoundException() {
        int walletId = 1;
        double amount = 1000;
        Transaction transaction = TransactionFactory.get().withAmount(amount).build(Type.DEBIT);
        Mockito.when(walletService.fetchById(walletId)).thenThrow(WalletNotFoundException.class);

        Assertions.assertThrows(WalletNotFoundException.class, () -> transactionService.add(walletId, transaction));
    }

    @Test
    void add_shouldThrowInsufficientBalanceException_whenWalletBalanceInsufficient() {
        int walletId = 1;
        double amount = 1000;
        Wallet wallet = WalletFactory.get().withId(walletId).build();
        Transaction transaction = TransactionFactory.get().withAmount(amount).withWallet(wallet).build(Type.DEBIT);
        Mockito.when(walletService.fetchById(walletId)).thenReturn(wallet);

        Assertions.assertThrows(InsufficientBalanceException.class,
                () -> transactionService.add(walletId, transaction));
    }

    @Test
    void fetchByWalletId_shouldReturnListOfTransaction_whenWalletExist() {
        int walletId = 1;
        double amount = 1000;
        Wallet wallet = WalletFactory.get().withId(walletId).build();
        Transaction transaction = TransactionFactory.get().withAmount(amount).withWallet(wallet).build(Type.DEBIT);
        List<Transaction> transactions = Collections.singletonList(transaction);
        Mockito.when(walletService.fetchById(walletId)).thenReturn(wallet);
        Mockito.when(transactionRepository.findAllByWallet(wallet)).thenReturn(transactions);

        List<Transaction> actualResult = transactionService.fetchByWalletId(walletId);

        Assertions.assertEquals(transactions, actualResult);
    }

    @Test
    void fetchByWalletId_shouldThrowWalletNotFoundException_whenWalletNotExist() {
        int walletId = 1;
        Mockito.when(walletService.fetchById(walletId)).thenThrow(WalletNotFoundException.class);

        Assertions.assertThrows(WalletNotFoundException.class, () -> transactionService.fetchByWalletId(walletId));
    }
}
