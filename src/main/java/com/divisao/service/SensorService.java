package com.divisao.service;

import com.divisao.common.CoverterMapperSensor;
import com.divisao.dto.FaturamentoDto;
import com.divisao.dto.SensorOutputDto;
import com.divisao.exception.NegocioException;
import com.divisao.model.Sensor;
import com.divisao.repository.LoteRepository;
import com.divisao.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SensorService {
    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private LoteRepository loteRepository;

    @Autowired
    private CoverterMapperSensor coverterMapperSensor;

    @Transactional
    public List<Sensor> salvar(Long quantidade) {

        for (int i = 0; i < quantidade; i++) {

            Sensor sensor = new Sensor();

            if(Math.random() < 0.07) {
                sensor.setDefeito(true);
                sensorRepository.save(sensor);
            }

            sensor.setDefeito(false);
            sensorRepository.save(sensor);
        }

        return sensorRepository.findAll();
    }

    public List<SensorOutputDto> listar() {

        return coverterMapperSensor.toCollectionModel(sensorRepository.findAll());
    }

    @Transactional
    public void deletar() {

        sensorRepository.deleteAll();
        loteRepository.deleteAll();

    }

    public FaturamentoDto obterFaturamento() {

        List<Sensor> sensores = sensorRepository.findAll();

        if(sensores.isEmpty()) {
            throw new NegocioException("Nenhum sensor cadastrado.");
        }

        int valor = 0;

        for (Sensor sensor : sensores) {
            if(sensor.getDefeito().equals(false)){
                valor += 30;
            }
        }

        return FaturamentoDto.builder().valor(valor).build();
    }


}
