package com.indrachrsm.ewallet.systemparameter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SystemParameterLoader {
    public static final String CACHE_NAME = "SystemParameter";

    private static final Logger logger = LoggerFactory.getLogger(SystemParameterLoader.class);

    private final SystemParameterRepository systemParamRepository;


    @Cacheable(value = CACHE_NAME, key = "#parameterName")
    public SystemParameter getSystemParameterValue(String parameterName) {
        logger.info("Get System Parameter {}", parameterName);
        return systemParamRepository.findByName(parameterName);
    }

    @CachePut(value = CACHE_NAME, key = "#systemParameter.name")
    public void updateSystemParameter(SystemParameter systemParameter) {
        logger.info("Update System Parameter {}", systemParameter.getName());
    }

}
