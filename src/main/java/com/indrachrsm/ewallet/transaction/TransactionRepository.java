package com.indrachrsm.ewallet.transaction;

import java.util.List;

import com.indrachrsm.ewallet.wallet.Wallet;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAllByWallet(Wallet wallet);
}
