package br.com.sicredi.woop.pauta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.sicredi.woop.pauta.enums.SimNaoEnum;
import br.com.sicredi.woop.pauta.model.Voto;
import br.com.sicredi.woop.pauta.service.VotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Serviços relacionadas a Voto.")
@RestController
@RequestMapping("/voto")
public class VotoController {

    @Autowired
    private VotoService service;

    @ApiOperation(
            value = "Votar em uma Pauta.",
            notes = "Realizar Voto em uma Pauta valida.")
    @PostMapping
    public ResponseEntity votar(@RequestParam(value = "idPauta", required = true) String idPauta, 
					    		@RequestParam(value = "numeroMatricula", required = true) String numeroMatricula,
							    @RequestParam(value = "voto", required = true) SimNaoEnum voto) {
    	service.votar(idPauta, new Voto(numeroMatricula, voto));
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
