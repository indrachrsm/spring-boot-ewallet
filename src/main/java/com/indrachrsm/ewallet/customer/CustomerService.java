package com.indrachrsm.ewallet.customer;

import com.indrachrsm.ewallet.wallet.Wallet;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository userRepository;

    public Customer create(Customer user) {
        Wallet wallet = Wallet.builder().build();
        user.setWallet(wallet);
        return userRepository.save(user);
    }

    public Customer fetchById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(CustomerNotFoundException::new);
    }

    public Customer update(Integer userId, CustomerReqDto userRequestDto) {
        Customer user = fetchById(userId);
        user.update(userRequestDto);

        return userRepository.save(user);
    }
}
