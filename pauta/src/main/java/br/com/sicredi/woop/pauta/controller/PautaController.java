package br.com.sicredi.woop.pauta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.sicredi.woop.pauta.model.Pauta;
import br.com.sicredi.woop.pauta.service.PautaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Pauta", description = "Serviços relacionadas a Pauta.")
@RestController
@RequestMapping("/pauta")
public class PautaController {

    @Autowired
    private PautaService service;

    @ApiOperation(
            value = "Criar uma Pauta.",
            notes = "Cadastra inicial de Pauta.")
    @PostMapping
    public ResponseEntity cadastrarPauta(@RequestParam(value = "titulo", required = true) String titulo,
    								     @RequestParam(value = "descricao", required = false) String descricao) {
    	service.criarPauta(titulo, descricao);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "Buscar uma Pauta.",
            notes = "Buscar uma Pauta por iD.")
    @GetMapping("buscar/{idPauta}")
    public ResponseEntity<Pauta> encontrarPauta(@PathVariable("idPauta") String idPauta) {
        return service.buscarPautaPorId(idPauta).map(pauta -> new ResponseEntity<>(pauta, HttpStatus.OK))
        												.orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
    
    @ApiOperation(
            value = "Listar Pautas.",
            notes = "Listagem de Pautas Cadastradas - Paginada.")
    @GetMapping("/listar")
    public ResponseEntity<Page<Pauta>> buscarPautas(@RequestParam(value = "page", defaultValue = "0") int page,
                    							    @RequestParam(value = "size", defaultValue = "25") int size) {
        return new ResponseEntity<>(service.buscarTodasPautas(PageRequest.of(page, size)), HttpStatus.OK);
    }
}
