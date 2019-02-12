package br.com.sicredi.woop.associado.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.sicredi.woop.associado.model.Associado;

public interface AssociadoRepository extends MongoRepository<Associado, String> {
	Associado findByNumeroMatricula(String numeroMatricula);
}
