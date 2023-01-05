package com.divisao.controller;

import com.divisao.dto.LoteAprovadoDto;
import com.divisao.model.Lote;
import com.divisao.service.LoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<Lote>> criar(int quantidadePorLote) {
        return ResponseEntity.ok(loteService.criar(quantidadePorLote));
    }

    @GetMapping("checar")
    public ResponseEntity<List<LoteAprovadoDto>> checarLotes(){
        return ResponseEntity.ok(loteService.checarLotes());
    }
}
