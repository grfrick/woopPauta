package br.com.sicredi.woop.associado.controller;

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

import br.com.sicredi.woop.associado.model.Associado;
import br.com.sicredi.woop.associado.service.AssociadoService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/associado")
public class AssociadoController {

    @Autowired
    private AssociadoService service;

    @ApiOperation(
            value = "Criar Associado.",
            notes = "Cria um novo Associado")
    @PostMapping
    public ResponseEntity criarAssociado(@RequestParam(value = "nome", required = true) String nome,
    								     @RequestParam(value = "numeroMatricula", required = true) String numeroMatricula) {
    	service.criar(nome, numeroMatricula);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @ApiOperation(
            value = "Listar Associados.",
            notes = "Listagem de Associados Cadastrados - Paginada.")
    @GetMapping("/listar")
    public ResponseEntity<Page<Associado>> buscarAssociados(@RequestParam(value = "page", defaultValue = "0") int page,
                    							      	 @RequestParam(value = "size", defaultValue = "25") int size) {
        return new ResponseEntity<>(service.buscarTodosAssociados(PageRequest.of(page, size)), HttpStatus.OK);
    }
    
    @ApiOperation(
            value = "Buscar um Associado.",
            notes = "Buscar um Associado pelo numeroMatricula.")
    @GetMapping("buscar/{numeroMatricula}")
    public ResponseEntity<Associado> buscarAssociado(@PathVariable("numeroMatricula") String numeroMatricula) {
        return service.buscarAssociado(numeroMatricula).map(associado -> new ResponseEntity<>(associado, HttpStatus.OK))
        										  .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
