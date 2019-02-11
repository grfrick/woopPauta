package br.com.sicredi.woop.pauta.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sicredi.woop.pauta.exception.WoopException;
import br.com.sicredi.woop.pauta.model.Pauta;
import br.com.sicredi.woop.pauta.model.Sessao;
import br.com.sicredi.woop.pauta.model.Voto;
import br.com.sicredi.woop.pauta.repository.PautaRepository;

@Service
public class VotoService {
	
	@Autowired
	public PautaRepository repository;

	public Pauta votar(String id, Voto voto) {
        Pauta pauta = repository.findById(id).orElseThrow(() -> new WoopException(id));

        validaPauta(id, pauta.getSessao());
        validaVotoRepetido(id, voto.getIdEleitor(), pauta.getSessao().getVotos());
        validaPeriodoVotacao(pauta);

        // TODO
        // validaEleitorCadastrado
        
        pauta.getSessao().getVotos().add(voto);
        
        return repository.save(pauta);
    }

	private void validaPeriodoVotacao(Pauta pauta) {
		if (LocalDateTime.now().isAfter(pauta.getSessao().getFim())) 
            throw new WoopException("A sessão já encerrou, não é mais possivel votar");
	}

	private void validaVotoRepetido(String id, String idEleitor, Collection<Voto> votos) {
		Optional<Voto> temVoto = votos.stream()
							            .filter(v -> v.getIdEleitor().equals(idEleitor))
							            .findFirst();

        if (temVoto.isPresent()) 
        	throw new WoopException("Eleitor [" + idEleitor + "] já votou na Pauta [" + id + "].");
	}

	private void validaPauta(String id, Sessao sessao) {
		if (null == sessao) 
        	throw new WoopException("Sessão da Pauta [" + id + "] não encontrada.");
	}
}