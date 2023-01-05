package com.divisao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoteAprovadoDto {
    private Long id;
    private Boolean aprovado;
}
