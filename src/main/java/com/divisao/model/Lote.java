package com.divisao.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "lote")
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<Sensor> sensores;


    public boolean checarAprovado() {
        int quantidadeSensores = getSensores().size();
        int quantidadeSensoresComDefeito = (int) getSensores()
                .stream().filter(Sensor::getDefeito).count();

        return ((double) quantidadeSensoresComDefeito / (double) quantidadeSensores) < 0.07;
    }
}
