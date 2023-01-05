package com.divisao.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class ReparoDto {

    private Integer valor;
    private Integer quantidadeReparados;

}