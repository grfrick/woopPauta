package br.com.sicredi.woop.pauta.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.sicredi.woop.pauta.model.Pauta;
import br.com.sicredi.woop.pauta.repository.PautaRepository;

@Service
public class PautaService {

    @Autowired
    private PautaRepository repository;

    public Pauta criarPauta(String titulo, String descricao) {
        return repository.save(new Pauta(titulo, descricao));
    }

    public Optional<Pauta> buscarPautaPorId(String idPauta) {
        return repository.findById(idPauta);
    }
    
    public Page<Pauta> buscarTodasPautas(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }
    
    public Pauta salvaPauta(Pauta pauta) {
        return repository.save(pauta);
    }
}