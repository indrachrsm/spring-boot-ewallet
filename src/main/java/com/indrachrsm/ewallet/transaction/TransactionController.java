package com.indrachrsm.ewallet.transaction;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping(path = "/wallets/{walletId}/transactions")
    public ResponseEntity<TransactionResponseDto> add(@PathVariable Integer walletId,
            @RequestBody TransactionRequestDto transactionRequest) {
        Transaction transaction = transactionRequest.convertTo(Transaction.class);
        Transaction savedTransaction = transactionService.add(walletId, transaction);
        TransactionResponseDto transactionResponse = savedTransaction.convertTo(TransactionResponseDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionResponse);
    }

    @GetMapping(path = "/wallets/{walletId}/transactions")
    public List<TransactionResponseDto> fetchByWalletId(@PathVariable Integer walletId) {
        List<Transaction> transactions = transactionService.fetchByWalletId(walletId);
        return transactions.stream().map(transaction -> transaction.convertTo(TransactionResponseDto.class))
                .collect(Collectors.toList());
    }

}
