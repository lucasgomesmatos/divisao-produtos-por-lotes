package com.divisao.common;

import java.util.ArrayList;
import java.util.List;

public class ListaUtils {

    public static <T> List<List<T>> paginarLista(List<T> lista, int tamanhoPagina) {
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

}
