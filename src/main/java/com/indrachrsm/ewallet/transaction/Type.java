package com.indrachrsm.ewallet.transaction;

public enum Type {
    DEBIT {
        @Override
        public Double convertAmount(Double amount) {
            int minusValue = -1;
            return amount * minusValue;
        }
    },
    CREDIT {
        @Override
        public Double convertAmount(Double amount) {
            return amount;
        }
    };

    public abstract Double convertAmount(Double amount);

}
