package com.indrachrsm.ewallet;

import org.modelmapper.ModelMapper;

public interface Convertable {
    default <T> T convertTo(Class<T> targetClass) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, targetClass);
    }
}
