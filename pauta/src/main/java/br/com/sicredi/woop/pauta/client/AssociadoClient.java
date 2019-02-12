package br.com.sicredi.woop.pauta.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.sicredi.woop.pauta.domain.Associado;

@FeignClient(name = "AssociadoClient", url="http://localhost:8091/")
public interface AssociadoClient {
	
	@GetMapping("associado/buscar/{numeroMatricula}")
	Associado buscarAssociado(@PathVariable("numeroMatricula") String numeroMatricula);
}
