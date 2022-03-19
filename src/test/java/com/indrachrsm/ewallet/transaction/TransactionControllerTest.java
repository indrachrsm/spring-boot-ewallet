package com.indrachrsm.ewallet.transaction;

import java.util.Collections;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.indrachrsm.ewallet.factories.TransactionFactory;
import com.indrachrsm.ewallet.factories.WalletFactory;
import com.indrachrsm.ewallet.kafka.KafkaProducerService;
import com.indrachrsm.ewallet.wallet.Wallet;
import com.indrachrsm.ewallet.wallet.WalletRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {
    @Autowired
    private MockMvc clientMock;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @MockBean
    private KafkaProducerService kafkaProducerService;

    private final Faker faker = new Faker();

    @BeforeEach
    void cleanUp() {
        transactionRepository.deleteAllInBatch();
        walletRepository.deleteAllInBatch();
    }

    @Test
    void add_shouldReturn201AndReturnSavedTransactionAndIncreaseWalletBalance_whenWalletIsExistAndTransactionTypeIsCredit()
            throws Exception {
        Wallet wallet = WalletFactory.get().withBalance(1000).build();
        Wallet savedWallet = walletRepository.save(wallet);
        double amount = 1000.0;
        TransactionRequestDto transactionRequest = TransactionRequestDto.builder().type(Type.CREDIT).amount(amount)
                .build();
        String requestJson = objectMapper.writeValueAsString(transactionRequest);
        String url = "/wallets/" + savedWallet.getId() + "/transactions";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(url).content(requestJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = clientMock.perform(request).andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        TransactionResponseDto transactionResponse = objectMapper.readValue(jsonResponse, TransactionResponseDto.class);
        Transaction savedTransaction = transactionRepository.findById(transactionResponse.getId()).get();
        TransactionResponseDto expectedResponse = savedTransaction.convertTo(TransactionResponseDto.class);
        Wallet updatedWallet = walletRepository.findById(savedWallet.getId()).get();
        double expectedBalance = savedWallet.getBalance() + amount;

        Assertions.assertEquals(expectedResponse, transactionResponse);
        Assertions.assertEquals(expectedBalance, updatedWallet.getBalance());
    }

    @Test
    void add_shouldReturn201AndReturnSavedTransactionAndDecreaseWalletBalance_whenWalletIsExistAndTransactionTypeIsDebit()
            throws Exception {
        Wallet wallet = WalletFactory.get().withBalance(1000).build();
        Wallet savedWallet = walletRepository.save(wallet);
        double amount = 1000.0;
        TransactionRequestDto transactionRequest = TransactionRequestDto.builder().type(Type.DEBIT).amount(amount)
                .build();
        String requestJson = objectMapper.writeValueAsString(transactionRequest);
        String url = "/wallets/" + savedWallet.getId() + "/transactions";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(url).content(requestJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = clientMock.perform(request).andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        TransactionResponseDto transactionResponse = objectMapper.readValue(jsonResponse, TransactionResponseDto.class);
        Transaction savedTransaction = transactionRepository.findById(transactionResponse.getId()).get();
        TransactionResponseDto expectedResponse = savedTransaction.convertTo(TransactionResponseDto.class);
        Wallet updatedWallet = walletRepository.findById(savedWallet.getId()).get();
        double expectedBalance = savedWallet.getBalance() - amount;

        Assertions.assertEquals(expectedResponse, transactionResponse);
        Assertions.assertEquals(expectedBalance, updatedWallet.getBalance());
    }

    @Test
    void add_shouldReturn404_whenWalletIsNotExist() throws Exception {
        int walletId = faker.number().randomDigit();
        double amount = 1000.0;
        TransactionRequestDto transactionRequest = TransactionRequestDto.builder().type(Type.DEBIT).amount(amount)
                .build();
        String requestJson = objectMapper.writeValueAsString(transactionRequest);
        String url = "/wallets/" + walletId + "/transactions";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(url).content(requestJson)
                .contentType(MediaType.APPLICATION_JSON);

        clientMock.perform(request).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void add_shouldReturn406_whenWalletBalanceIsInsufficientAndTransactionTypeIsDebit() throws Exception {
        Wallet wallet = WalletFactory.get().withBalance(1000).build();
        Wallet savedWallet = walletRepository.save(wallet);
        double amount = 2000.0;
        TransactionRequestDto transactionRequest = TransactionRequestDto.builder().type(Type.DEBIT).amount(amount)
                .build();
        String requestJson = objectMapper.writeValueAsString(transactionRequest);
        String url = "/wallets/" + savedWallet.getId() + "/transactions";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(url).content(requestJson)
                .contentType(MediaType.APPLICATION_JSON);

        clientMock.perform(request).andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }

    @Test
    void fetchByWalletId_shouldReturn200AndListOfTransactions_whenWalletExist() throws Exception {
        Wallet wallet = WalletFactory.get().withBalance(1000).build();
        Wallet savedWallet = walletRepository.save(wallet);
        Transaction transaction = TransactionFactory.get().withWallet(savedWallet).withAmount(1000).build(Type.CREDIT);
        Transaction savedTransaction = transactionRepository.save(transaction);
        TransactionResponseDto savedTransactionResponse = savedTransaction.convertTo(TransactionResponseDto.class);
        List<TransactionResponseDto> transactionsResponse = Collections.singletonList(savedTransactionResponse);
        String expectedResponse = objectMapper.writeValueAsString(transactionsResponse);
        String url = "/wallets/" + savedWallet.getId() + "/transactions";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(url);
        MvcResult result = clientMock.perform(request).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String actualResponse = result.getResponse().getContentAsString();

        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void fetchByWalletId_shouldReturn404_whenWalletNotExist() throws Exception {
        int walletId = faker.number().randomDigit();
        String url = "/wallets/" + walletId + "/transactions";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(url);
        clientMock.perform(request).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
