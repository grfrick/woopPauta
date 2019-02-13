package br.com.sicredi.woop.pauta.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.sicredi.woop.pauta.domain.Resultado;
import br.com.sicredi.woop.pauta.service.SessaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Serviços relacionadas a Sessao de Votação.")
@RestController
@RequestMapping("/sessao")
public class SessaoController {

    @Autowired
    private SessaoService service;

    @ApiOperation(
            value = "Iniciar a sessão de uma Pauta.",
            notes = "Inicia a sessão de uma unica Pauta.")    
    @PostMapping("/iniciarSessao")
    public ResponseEntity iniciarSessao(@RequestParam(value = "idPauta", required = true) String idPauta, 
								    	@RequestParam(value = "inicio", required = false) LocalDateTime inicio,
										@RequestParam(value = "fim", required = false) LocalDateTime fim) {
    	service.iniciarSessao(idPauta, inicio, fim);
        return new ResponseEntity(HttpStatus.CREATED);
    }
   
    @ApiOperation(
            value = "Buscar a contagem de Votos de uma Pauta.",
            notes = "Deve buscar os totais de votos realizados numa sessão de uma pauta.")    
    @GetMapping("/resultado")
    public ResponseEntity<Resultado> buscarResultadoVotacaoPauta(@RequestParam(value = "idPauta", required = true) String idPauta) {
    	return new ResponseEntity(service.resultadoVotacaoPauta(idPauta), HttpStatus.OK);
    }
}
