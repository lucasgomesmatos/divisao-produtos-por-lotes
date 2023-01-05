package com.divisao.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class QuantidadeSensoresDto {

    @NotNull
    private Integer QuantidadeSensores;
}
