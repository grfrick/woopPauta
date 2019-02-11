package br.com.sicredi.woop.pauta.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.sicredi.woop.pauta.exception.WoopException;
import br.com.sicredi.woop.pauta.model.Pauta;
import br.com.sicredi.woop.pauta.model.Voto;
import br.com.sicredi.woop.pauta.repository.PautaRepository;

@Service
public class PautaService {

    @Autowired
    private PautaRepository repository;

    public Pauta votar(String id, Voto voto) {
        Pauta pauta = repository.findById(id).orElseThrow(() -> new WoopException(id));

        if (null == pauta.getSessao()) 
        	throw new WoopException("Sessão da Pauta [" + id + "] não encontrada.");

        Optional<Voto> jaVotou = pauta.getSessao().getVotos().stream()
            .filter(votos -> votos.getIdEleitor().equals(voto.getIdEleitor()))
            .findFirst();

        if (jaVotou.isPresent()) 
        	throw new WoopException("Eleitor [" + voto.getIdEleitor() + "] já votou na Pauta [" + id + "].");
        
        if (LocalDateTime.now().isAfter(pauta.getSessao().getFim())) 
            throw new WoopException("A sessão já encerrou, não é mais possivel votar");

        pauta.getSessao().getVotos().add(voto);
        
        return repository.save(pauta);
    }

    public Pauta criarPauta(String titulo, String descricao) {
        return repository.save(new Pauta(titulo, descricao));
    }

    public Optional<Pauta> buscarPautaPorId(String id) {
        return repository.findById(id);
    }
    
    public Page<Pauta> buscarTodasPautas(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }
}