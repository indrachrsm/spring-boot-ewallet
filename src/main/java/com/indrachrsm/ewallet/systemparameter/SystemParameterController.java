package com.indrachrsm.ewallet.systemparameter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SystemParameterController {
    private final SystemParameterService systemParameterService;
    private final SystemParameterLoader systemParameterLoader;

    @PostMapping(path = "/system-parameters")
    public ResponseEntity<SystemParameterDto> post(@RequestBody SystemParameterDto systemParameterReqDto) {
        SystemParameter systemParameter = systemParameterReqDto.convertTo(SystemParameter.class);
        SystemParameter savedSystemParameter = systemParameterService.save(systemParameter);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSystemParameter.convertTo(SystemParameterDto.class));
    }
    
    @GetMapping(path = "system-parameters")
    public ResponseEntity<SystemParameterDto> get(@RequestParam String name) {
        SystemParameter systemParameter = systemParameterLoader.getSystemParameterValue(name);
        return ResponseEntity.status(HttpStatus.OK).body(systemParameter.convertTo(SystemParameterDto.class));
    }
}
