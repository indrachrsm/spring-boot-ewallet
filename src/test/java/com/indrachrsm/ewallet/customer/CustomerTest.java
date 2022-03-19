package com.indrachrsm.ewallet.customer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CustomerTest {
    @Test
    void update_shouldChangeName() {
        String newName = "John Trakowski";
        Customer user = Customer.builder().name("John Doe").build();
        Customer expectedUser = Customer.builder().name(newName).build();
        CustomerReqDto userRequest = CustomerReqDto.builder().name(newName).build();

        user.update(userRequest);

        Assertions.assertEquals(expectedUser, user);
    }
}
