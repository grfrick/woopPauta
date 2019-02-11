package br.com.sicredi.woop.pauta.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.sicredi.woop.pauta.client.EleitorClient;
import br.com.sicredi.woop.pauta.exception.EleitorWoopException;
import br.com.sicredi.woop.pauta.exception.PautaWoopException;
import br.com.sicredi.woop.pauta.exception.SessaoWoopException;
import br.com.sicredi.woop.pauta.exception.VotoWoopException;
import br.com.sicredi.woop.pauta.model.Pauta;
import br.com.sicredi.woop.pauta.model.Sessao;
import br.com.sicredi.woop.pauta.model.Voto;
import br.com.sicredi.woop.pauta.repository.PautaRepository;

@Service
public class VotoService {
	
	@Autowired
	public PautaRepository repository;
	
	@Autowired
	public EleitorClient client;

	public Pauta votar(String id, Voto voto) {
        Pauta pauta = repository.findById(id).orElseThrow(() -> new PautaWoopException(HttpStatus.NOT_FOUND, id));

        validaPeriodoVotacao(pauta);        
        validaEleitorValido(voto.getIdEleitor());
        validaPauta(id, pauta.getSessao());
        validaVotoRepetido(id, voto.getIdEleitor(), pauta.getSessao().getVotos());
        
        pauta.getSessao().getVotos().add(voto);
        
        return repository.save(pauta);
    }

	private void validaEleitorValido(String tituloEleitor) {
		if (null == client.buscarEleitor(tituloEleitor)) {
			throw new EleitorWoopException(HttpStatus.NOT_FOUND, "Eleitor não é válido com o título [" + tituloEleitor + "]");
		}
	}

	private void validaPeriodoVotacao(Pauta pauta) {
		if (LocalDateTime.now().isAfter(pauta.getSessao().getFim())) 
            throw new VotoWoopException(HttpStatus.UNAUTHORIZED, "A sessão já encerrou, não é mais possivel votar. Seja mais rapido da próxima vez =]");
	}

	private void validaVotoRepetido(String id, String idEleitor, Collection<Voto> votos) {
		Optional<Voto> temVoto = votos.stream()
							            .filter(v -> v.getIdEleitor().equals(idEleitor))
							            .findFirst();

        if (temVoto.isPresent()) 
        	throw new EleitorWoopException(HttpStatus.UNAUTHORIZED,"Eleitor [" + idEleitor + "] já votou na Pauta [" + id + "].");
	}

	private void validaPauta(String id, Sessao sessao) {
		if (null == sessao) 
        	throw new SessaoWoopException(HttpStatus.NOT_FOUND, "Sessão da Pauta [" + id + "] não encontrada.");
	}
}