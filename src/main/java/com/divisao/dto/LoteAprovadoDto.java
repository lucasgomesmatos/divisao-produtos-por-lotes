package com.divisao.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoteAprovadoDto {
    private Long id;
    private Boolean aprovado;
}
