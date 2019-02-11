package br.com.sicredi.woop.pauta.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.sicredi.woop.pauta.model.Sessao;

public interface SessaoRepository extends MongoRepository<Sessao, String> {
}
