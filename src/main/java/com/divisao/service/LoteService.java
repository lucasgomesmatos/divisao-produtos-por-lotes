package com.divisao.service;
import com.divisao.dto.FaturamentoDto;
import com.divisao.dto.LoteAprovadoDto;
import com.divisao.dto.ReparoDto;
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


import static com.divisao.common.ListaUtils.paginarLista;

@Service
public class LoteService {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private LoteRepository loteRepository;

    public List<Lote> obterListaDeLotes() {
        List<Lote> lotes = loteRepository.findAll();

        if (lotes.isEmpty()) {
            throw new NegocioException("Nenhum sensor cadastrado.");
        }

        return lotes;
    }

    @Transactional
    public List<Lote> criar(int quantidadePorLote) {

        List<Sensor> sensores = sensorRepository.findAll();
        if (sensores.isEmpty()) {
            throw new NegocioException("Nenhum sensor cadastrado.");
        }

        List<List<Sensor>> listasPaginados = paginarLista(sensores, quantidadePorLote);

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

    public List<LoteAprovadoDto> checarLotesAprovados() {

        List<Lote> lotes = obterListaDeLotes();

        List<LoteAprovadoDto> aprovados = new ArrayList<>(lotes.size());

        for (Lote lote : lotes) {
            aprovados.add(LoteAprovadoDto.builder()
                    .id(lote.getId())
                    .aprovado(lote.checarAprovado())
                    .build());
        }

        return aprovados;
    }

    public ReparoDto consertarLotes() {

        List<Lote> lotes = obterListaDeLotes();

        int quantidadeReparados = 0;

        for (Lote lote : lotes) {
            if (lote.checarAprovado()) continue;
            for (Sensor sensor : lote.getSensores()) {
                if (sensor.getDefeito()) {
                    sensor.setDefeito(false);
                    quantidadeReparados += 1;
                }
            }

            loteRepository.save(lote);
        }

        return new ReparoDto(quantidadeReparados * 20, quantidadeReparados);
    }

    public FaturamentoDto obterFaturamento() {

        List<Lote> lotes = obterListaDeLotes();
        int valorTotal = 0;

        for (Lote lote : lotes) {
            if (lote.checarAprovado()) {
                valorTotal += lote.getSensores().size() * 30;
            }
        }

        return new FaturamentoDto(valorTotal);
    }

}
