package com.indrachrsm.ewallet.factories;

import com.indrachrsm.ewallet.wallet.Wallet;

public class WalletFactory {
    private Integer id = null;
    private double balance = 0.0;

    public static WalletFactory get() {
        return new WalletFactory();
    }

    public WalletFactory withId(int id) {
        this.id = id;
        return this;
    }

    public WalletFactory withBalance(double balance) {
        this.balance = balance;
        return this;
    }

    public Wallet build() {
        return Wallet.builder().id(this.id).balance(this.balance).build();
    }
}
