package com.divisao.common;

import com.divisao.dto.SensorOutputDto;
import com.divisao.model.Sensor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CoverterMapperSensor {

    @Autowired
    private ModelMapper mapper;

    public SensorOutputDto toModel(Sensor sensor){
        return mapper.map(sensor, SensorOutputDto.class);
    }

    public List<SensorOutputDto> toCollectionModel(List<Sensor> sensors) {
        return sensors.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
