package br.com.sicredi.woop.pauta.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.sicredi.woop.pauta.model.Voto;

public interface VotoRepository extends MongoRepository<Voto, String> {
}
