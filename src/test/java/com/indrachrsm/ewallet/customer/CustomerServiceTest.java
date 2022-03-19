package com.indrachrsm.ewallet.customer;

import java.util.Optional;

import com.indrachrsm.ewallet.factories.CustomerFactory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    private CustomerRepository userRepository;
    @InjectMocks
    private CustomerService userService;

    @Test
    void create_shouldReturnSavedUserFromRepository() {
        Customer user = CustomerFactory.get().build();
        Mockito.when(userRepository.save(user)).thenReturn(user);

        Customer actualResult = userService.create(user);

        Assertions.assertEquals(user, actualResult);
    }

    @Test
    void fetchById_shouldReturnUser_whenUserExist() {
        int userId = 1;
        Customer user = CustomerFactory.get().withId(userId).build();
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Customer actualResult = userService.fetchById(userId);

        Assertions.assertEquals(user, actualResult);
    }

    @Test
    void fetchById_shouldThrowUserNotFoundException_whenUserNotExist() {
        int userId = 1;
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Assertions.assertThrows(CustomerNotFoundException.class, () -> userService.fetchById(userId));
    }

    @Test
    void update_shouldReturnUpdatedUser_whenUserExist() {
        int userId = 1;
        String newName = "John Trakowski";
        Customer user = CustomerFactory.get().withId(userId).build();
        Customer expectedUser = CustomerFactory.get().withId(userId).withName(newName).build();
        CustomerReqDto userRequest = CustomerReqDto.builder().name(newName).build();
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(expectedUser)).thenReturn(expectedUser);

        Customer actualResult = userService.update(userId, userRequest);

        Assertions.assertEquals(user, actualResult);
    }

    @Test
    void update_shouldThrowUserNotFoundException_whenUserNotExist() {
        int userId = 1;
        CustomerReqDto userRequest = CustomerReqDto.builder().name("John Trakowski").build();
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Assertions.assertThrows(CustomerNotFoundException.class, () -> userService.update(userId, userRequest));
    }
}
