package com.indrachrsm.ewallet.systemparameter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.indrachrsm.ewallet.kafka.KafkaProducerService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SystemParameterService {
    private final SystemParameterLoader systemParameterLoader;
    private final ObjectMapper objectMapper;
    private final KafkaProducerService kafkaProducerService;
    private final SystemParameterRepository systemParameterRepository;

    public SystemParameter save(SystemParameter systemParameter) throws JsonProcessingException {
        String dataToSend = objectMapper.writeValueAsString(systemParameter);
        SystemParameter savedSystemParameter = systemParameterRepository.save(systemParameter);
        kafkaProducerService.sendData(dataToSend, "SYSTEM_PARAMETER");
        systemParameterLoader.updateSystemParameter(systemParameter);
        return savedSystemParameter;
    }

}
