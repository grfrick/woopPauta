package br.com.sicredi.woop.pauta.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.sicredi.woop.pauta.domain.Eleitor;

@FeignClient(name = "EleitorClient", url="http://localhost:8091/")
public interface EleitorClient {
	
	@GetMapping("eleitor/buscar/{numeroTitulo}")
	Eleitor buscarEleitor(@PathVariable("numeroTitulo") String numeroTitulo);
}
