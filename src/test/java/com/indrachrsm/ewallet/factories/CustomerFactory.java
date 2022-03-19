package com.indrachrsm.ewallet.factories;

import com.github.javafaker.Faker;
import com.indrachrsm.ewallet.customer.Customer;
import com.indrachrsm.ewallet.wallet.Wallet;

public class CustomerFactory {
    private final Faker faker = new Faker();
    private Integer id = null;
    private String name = faker.name().fullName();
    private Wallet wallet = WalletFactory.get().build();

    public static CustomerFactory get() {
        return new CustomerFactory();
    }

    public CustomerFactory withId(int id) {
        this.id = id;
        return this;
    }

    public CustomerFactory withName(String name) {
        this.name = name;
        return this;
    }

    public Customer build() {
        return Customer.builder().id(this.id).name(this.name).wallet(this.wallet).build();
    }
}
