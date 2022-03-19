package com.indrachrsm.ewallet.transaction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TypeTest {

    @Test
    void convertAmount_shouldReturnNegativeValueOfAmount_whenTypeIsDebit() {
        double amount = 1000.0;
        double expectedValue = amount * -1;
        double convertedAmount = Type.DEBIT.convertAmount(amount);

        Assertions.assertEquals(expectedValue, convertedAmount);
    }

    @Test
    void convertAmount_shouldReturnAmount_whenTypeIsDebit() {
        double amount = 1000.0;
        double convertedAmount = Type.CREDIT.convertAmount(amount);

        Assertions.assertEquals(amount, convertedAmount);
    }
}
