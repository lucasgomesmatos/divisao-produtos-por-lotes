package com.divisao.service;

import com.divisao.common.CoverterMapperSensor;
import com.divisao.common.Mapper;
import com.divisao.dto.LoteAprovadoDto;
import com.divisao.exception.NegocioException;
import com.divisao.model.Lote;
import com.divisao.model.Sensor;
import com.divisao.repository.LoteRepository;
import com.divisao.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


import static com.divisao.common.ListaUtils.checarAprovacaoLotes;
import static com.divisao.common.ListaUtils.paginarLista;

@Service
public class LoteService {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private LoteRepository loteRepository;

    @Autowired
    private Mapper mapper;

    @Transactional
    public List<Lote> criar(int quantidadePorLote) {

        List<Sensor> sensores = sensorRepository.findAll();
        if(sensores.isEmpty()) {
            throw new NegocioException("Nenhum sensor cadastrado.");
        }

        List<List<Sensor>> listasPaginados = paginarLista(sensores,quantidadePorLote);

        for (List<Sensor> sensoresLista : listasPaginados) {

            Lote lote = Lote.builder()
                    .sensores(sensoresLista)
                    .build();

            loteRepository.save(lote);
        }

        return loteRepository.findAll();
    }

    public List<Lote> listar() {
        return loteRepository.findAll();
    }

    public List<LoteAprovadoDto> checarLotes(){
        List<Lote> lotes = loteRepository.findAll();
        if(lotes.isEmpty()) {
            throw new NegocioException("Nenhum lote cadastrado.");
        }

        return (List<LoteAprovadoDto>) checarAprovacaoLotes(lotes);
    }


}
