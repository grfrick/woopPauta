package br.com.sicredi.woop.pauta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.sicredi.woop.pauta.enums.SimNao;
import br.com.sicredi.woop.pauta.model.Voto;
import br.com.sicredi.woop.pauta.service.VotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Serviços relacionadas a Voto.")
@RestController
@RequestMapping("/voto")
public class VotoController {

    @Autowired
    private VotoService votoService;

    @ApiOperation(
            value = "Votar em uma Pauta.",
            notes = "Realizar Voto em uma Pauta valida.")
    @PostMapping("/{idPauta}")
    public ResponseEntity votar(@PathVariable("idPauta") String idPauta, 
					    		@RequestParam(value = "numeroTitulo", required = true) String numeroTitulo,
							    @RequestParam(value = "voto", required = false) SimNao voto) {
    	votoService.votar(idPauta, new Voto(numeroTitulo, voto));
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
