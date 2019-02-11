package br.com.sicredi.woop.eleitor.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.sicredi.woop.eleitor.model.Eleitor;

public interface EleitorRepository extends MongoRepository<Eleitor, String> {
	Eleitor findByNumeroTitulo(String numeroTitulo);
}
