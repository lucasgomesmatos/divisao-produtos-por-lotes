package com.divisao.controller;

import com.divisao.dto.FaturamentoDto;
import com.divisao.dto.LoteAprovadoDto;
import com.divisao.dto.ReparoDto;
import com.divisao.model.Lote;
import com.divisao.service.LoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/lotes")
public class LoteController {

    @Autowired
    private LoteService loteService;

    @GetMapping
    public ResponseEntity<List<Lote>> listar() {

        return ResponseEntity.ok(loteService.listar());
    }

    @GetMapping("checarLotesAprovados")
    public ResponseEntity<List<LoteAprovadoDto>> checarLotes(){

        return ResponseEntity.ok(loteService.checarLotesAprovados());
    }

    @GetMapping("obterFaturamento")
    public ResponseEntity<FaturamentoDto> obterFaturamento() {

        return  ResponseEntity.ok(loteService.obterFaturamento());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<Lote>> criar(@Valid int quantidadePorLote) {
        return ResponseEntity.ok(loteService.criar(quantidadePorLote));
    }


    @PutMapping("consertarLotes")
    public ResponseEntity<ReparoDto> consertarLotes() {

        return ResponseEntity.ok(loteService.consertarLotes());
    }


}
