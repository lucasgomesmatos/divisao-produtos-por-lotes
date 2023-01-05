package com.divisao.common;

import com.divisao.dto.LoteAprovadoDto;
import com.divisao.model.Lote;
import com.divisao.model.Sensor;

import java.util.ArrayList;
import java.util.List;

public class ListaUtils {

    public static  <T> List<List<T>> paginarLista(List<T> lista, int tamanhoPagina) {
        if (lista == null || lista.isEmpty())
            return new ArrayList<>();
        if (lista.size() < tamanhoPagina)
            tamanhoPagina = lista.size();

        int numeroPaginas = (int) Math.ceil((double) lista.size() / (double) tamanhoPagina);

        List<List<T>> paginas = new ArrayList<>(numeroPaginas);

        for (int pagina = 0; pagina < numeroPaginas; pagina++) {
            int limitePaginacaoAtual = Math.min((pagina + 1) * tamanhoPagina, lista.size());
            List<T> sublista = lista.subList(pagina * tamanhoPagina, limitePaginacaoAtual);
            paginas.add(sublista);
        }

        return paginas;
    }

    public static List<?> checarAprovacaoLotes(List<Lote> lotes) {

        List<LoteAprovadoDto> aprovados = new ArrayList<>(lotes.size());

        int quantidade = 0;
        int i = 0;

        for (Lote lote: lotes) {

            for(Sensor sensor: lote.getSensores()){
                if(sensor.getDefeito()) {
                    quantidade += 1;

                    System.out.println(sensor.getDefeito());
                }
            }

            if(quantidade < 7) {
                aprovados.add(i, LoteAprovadoDto.builder()
                        .id(lote.getId())
                        .aprovado(true)
                        .build());
            } else {
                aprovados.add(i, LoteAprovadoDto.builder()
                        .id(lote.getId())
                        .aprovado(false)
                        .build());
            }

            i++;
        }

        return aprovados;
    }
}
