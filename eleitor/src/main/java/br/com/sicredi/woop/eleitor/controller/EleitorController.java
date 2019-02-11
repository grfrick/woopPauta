package br.com.sicredi.woop.eleitor.controller;

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

import br.com.sicredi.woop.eleitor.model.Eleitor;
import br.com.sicredi.woop.eleitor.service.EleitorService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/eleitor")
public class EleitorController {

    @Autowired
    private EleitorService service;

    @ApiOperation(
            value = "Criar Eleitor.",
            notes = "Cria um novo Eleitor")
    @PostMapping
    public ResponseEntity criarEleitor(@RequestParam(value = "nome", required = true) String nome,
    								   @RequestParam(value = "numeroTitulo", required = true) String numeroTitulo) {
    	service.criar(nome, numeroTitulo);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @ApiOperation(
            value = "Listar Eleitores.",
            notes = "Listagem de Eleitores Cadastrados - Paginada.")
    @GetMapping("/listar")
    public ResponseEntity<Page<Eleitor>> buscarEleitores(@RequestParam(value = "page", defaultValue = "0") int page,
                    							      	 @RequestParam(value = "size", defaultValue = "25") int size) {
        return new ResponseEntity<>(service.buscarTodosEleitores(PageRequest.of(page, size)), HttpStatus.OK);
    }
    
    @ApiOperation(
            value = "Buscar um Eleitor.",
            notes = "Buscar um Eleitor pelo numeroTitulo.")
    @GetMapping("buscar/{numeroTitulo}")
    public ResponseEntity<Eleitor> buscarEleitor(@PathVariable("numeroTitulo") String numeroTitulo) {
        return service.buscarEleitor(numeroTitulo)
                        		.map(eleitor -> new ResponseEntity<>(eleitor, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
}
