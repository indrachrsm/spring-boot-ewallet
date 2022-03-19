package com.indrachrsm.ewallet.factories;

import com.indrachrsm.ewallet.transaction.Transaction;
import com.indrachrsm.ewallet.transaction.Type;
import com.indrachrsm.ewallet.wallet.Wallet;

public class TransactionFactory {
    private Integer id = null;
    private Wallet wallet = WalletFactory.get().build();
    private double amount = 0.0;

    public static TransactionFactory get() {
        return new TransactionFactory();
    }

    public TransactionFactory withId(int id) {
        this.id = id;
        return this;
    }

    public TransactionFactory withAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public TransactionFactory withWallet(Wallet wallet) {
        this.wallet = wallet;
        return this;
    }

    public Transaction build(Type type) {
        return Transaction.builder().id(this.id).amount(this.amount).wallet(this.wallet).type(type).build();
    }
}
