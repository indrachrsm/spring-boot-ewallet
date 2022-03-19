package com.indrachrsm.ewallet.systemparameter;

import com.indrachrsm.ewallet.Convertable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemParameterDto implements Convertable {
    private String name;
    private String value;
    private String description;
}
