package com.divisao.controller;

import com.divisao.dto.FaturamentoDto;
import com.divisao.dto.SensorOutputDto;
import com.divisao.model.Sensor;
import com.divisao.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/sensores")
public class SensorController {
    @Autowired
    private SensorService sensorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<Sensor>> salvar(@Valid @RequestBody Long quantidade) {
        return ResponseEntity.ok(sensorService.salvar(quantidade));
    }

    @GetMapping
    public ResponseEntity<List<SensorOutputDto>> listar() {

        return  ResponseEntity.ok(sensorService.listar());
    }

    @DeleteMapping
    public ResponseEntity<Void> deletar() {
        sensorService.deletar();
        return ResponseEntity.ok().build();
    }

    @GetMapping("obterFaturamento")
    public ResponseEntity<FaturamentoDto> obterFaturamento() {

        return  ResponseEntity.ok(sensorService.obterFaturamento());
    }
}
