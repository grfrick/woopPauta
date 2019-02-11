package br.com.sicredi.woop.pauta.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.sicredi.woop.pauta.model.Pauta;

public interface PautaRepository extends MongoRepository<Pauta, String> {
}
