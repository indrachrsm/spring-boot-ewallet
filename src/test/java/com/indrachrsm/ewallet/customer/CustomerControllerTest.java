package com.indrachrsm.ewallet.customer;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.indrachrsm.ewallet.factories.CustomerFactory;
import com.indrachrsm.ewallet.kafka.KafkaProducerService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
class CustomerControllerTest {
    @Autowired
    private MockMvc clientMock;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CustomerRepository userRepository;
    @MockBean
    private KafkaProducerService kafkaProducerService;

    private final Faker faker = new Faker();

    @AfterEach
    void cleanUp() {
        userRepository.deleteAllInBatch();
    }

    @Test
    void post_shouldReturn201AndAddUserWithWallet() throws Exception {
        CustomerReqDto userRequestDto = CustomerReqDto.builder().name("John Doe").build();
        String requestJson = objectMapper.writeValueAsString(userRequestDto);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/customers").content(requestJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = clientMock.perform(request).andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        CustomerRespDto userResponseDto = objectMapper.readValue(jsonResponse, CustomerRespDto.class);
        Customer savedUser = userRepository.findById(userResponseDto.getId()).get();
        CustomerRespDto expectedResponse = savedUser.convertTo(CustomerRespDto.class);

        Assertions.assertEquals(expectedResponse, userResponseDto);
    }

    @Test
    void fetchById_shouldReturn200AndUser_whenUserExist() throws Exception {
        Customer user = CustomerFactory.get().build();
        Customer savedUser = userRepository.save(user);
        String url = "/customers/" + savedUser.getId();
        CustomerRespDto expectedResponse = savedUser.convertTo(CustomerRespDto.class);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(url);
        MvcResult result = clientMock.perform(request).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        CustomerRespDto userResponseDto = objectMapper.readValue(jsonResponse, CustomerRespDto.class);

        Assertions.assertEquals(expectedResponse, userResponseDto);
    }

    @Test
    void fetchById_shouldReturn404_whenUserIsNotExist() throws Exception {
        String url = "/customers/" + faker.number().randomDigit();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(url);
        clientMock.perform(request).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void update_shouldReturn202_whenUserExist() throws Exception {
        Customer user = CustomerFactory.get().build();
        Customer savedUser = userRepository.save(user);
        String newName = "John Trakowski";
        savedUser.setName(newName);
        String url = "/customers/" + user.getId();
        CustomerReqDto userRequestDto = CustomerReqDto.builder().name(newName).build();
        String requestJson = objectMapper.writeValueAsString(userRequestDto);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(url).content(requestJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = clientMock.perform(request).andExpect(MockMvcResultMatchers.status().isAccepted())
                .andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        CustomerRespDto userResponseDto = objectMapper.readValue(jsonResponse, CustomerRespDto.class);
        CustomerRespDto expectedResponse = savedUser.convertTo(CustomerRespDto.class);

        Assertions.assertEquals(expectedResponse, userResponseDto);
    }

    @Test
    void update_shouldReturn404_whenUserIsNotExist() throws Exception {
        String url = "/customers/" + faker.number().randomDigit();
        CustomerReqDto userRequestDto = CustomerReqDto.builder().name("John Trakowski").build();
        String requestJson = objectMapper.writeValueAsString(userRequestDto);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(url).content(requestJson)
                .contentType(MediaType.APPLICATION_JSON);

        clientMock.perform(request).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
