package com.indrachrsm.ewallet.systemparameter;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SystemParameterService {
    private final SystemParameterLoader systemParameterLoader;
    private final SystemParameterRepository systemParameterRepository;

    public SystemParameter save(SystemParameter systemParameter) {
        SystemParameter savedSystemParameter = systemParameterRepository.save(systemParameter);
        systemParameterLoader.updateSystemParameter(savedSystemParameter);

        return savedSystemParameter;
    }

}
