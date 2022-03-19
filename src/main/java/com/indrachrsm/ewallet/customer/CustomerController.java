package com.indrachrsm.ewallet.customer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService userService;

    @PostMapping(path = "/customers")
    public ResponseEntity<CustomerRespDto> post(@RequestBody CustomerReqDto userRequestDto) {
        Customer convertedRequest = userRequestDto.convertTo(Customer.class);
        Customer savedUser = userService.create(convertedRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser.convertTo(CustomerRespDto.class));
    }

    @GetMapping(path = "/customers/{userId}")
    public CustomerRespDto fetchById(@PathVariable Integer userId) {
        Customer user = userService.fetchById(userId);
        return user.convertTo(CustomerRespDto.class);
    }

    @PutMapping(path = "/customers/{userId}")
    public ResponseEntity<CustomerRespDto> update(@PathVariable Integer userId,
            @RequestBody CustomerReqDto userRequestDto) {
        Customer updatedUser = userService.update(userId, userRequestDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedUser.convertTo(CustomerRespDto.class));
    }
}
