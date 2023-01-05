package com.divisao.service;

import com.divisao.common.CoverterMapperSensor;
import com.divisao.dto.QuantidadeSensoresDto;
import com.divisao.dto.SensorOutputDto;
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
    public List<Sensor> salvar(QuantidadeSensoresDto quantidade) {

        for (int i = 0; i < quantidade.getQuantidadeSensores() ; i++) {
            Sensor sensor = new Sensor();
            sensor.setDefeito(Math.random() < 0.07 );
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




}
