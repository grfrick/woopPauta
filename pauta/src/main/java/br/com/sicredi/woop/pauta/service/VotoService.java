package br.com.sicredi.woop.pauta.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.sicredi.woop.pauta.client.AssociadoClient;
import br.com.sicredi.woop.pauta.exception.WoopException;
import br.com.sicredi.woop.pauta.model.Pauta;
import br.com.sicredi.woop.pauta.model.Sessao;
import br.com.sicredi.woop.pauta.model.Voto;
import br.com.sicredi.woop.pauta.repository.PautaRepository;
import br.com.sicredi.woop.pauta.repository.VotoRepository;

@Service
public class VotoService {
	
	@Autowired
	public PautaRepository repository;
	
	@Autowired
	public VotoRepository votoRepository;
	
	@Autowired
	public AssociadoClient client;

	public Pauta votar(String id, Voto voto) {
        Pauta pauta = repository.findById(id).orElseThrow(() -> new WoopException(HttpStatus.NOT_FOUND, id));

        validaPeriodoVotacao(pauta);        
        validaAssociadoValido(voto.getNumeroMatricula());
        validaPauta(id, pauta.getSessao());
        validaVotoRepetido(id, voto.getNumeroMatricula(), pauta.getSessao().getVotos());
        
        pauta.getSessao().getVotos().add(votoRepository.save(voto));
        
        return repository.save(pauta);
    }

	private void validaAssociadoValido(String tituloAssociado) {
		if (null == client.buscarAssociado(tituloAssociado)) {
			throw new WoopException(HttpStatus.NOT_FOUND, "Associado não é válido com o título [" + tituloAssociado + "]");
		}
	}

	private void validaPeriodoVotacao(Pauta pauta) {
		if (LocalDateTime.now().isAfter(pauta.getSessao().getFim())) 
            throw new WoopException(HttpStatus.UNAUTHORIZED, "A sessão já encerrou, não é mais possivel votar. Seja mais rapido da próxima vez =]");
	}

	private void validaVotoRepetido(String id, String numeroMatricula, Collection<Voto> votos) {
		Optional<Voto> temVoto = votos.stream()
							            .filter(v -> v.getNumeroMatricula().equals(numeroMatricula))
							            .findFirst();

        if (temVoto.isPresent()) 
        	throw new WoopException(HttpStatus.UNAUTHORIZED,"Associado [" + numeroMatricula + "] já votou na Pauta [" + id + "].");
	}

	private void validaPauta(String id, Sessao sessao) {
		if (null == sessao) 
        	throw new WoopException(HttpStatus.NOT_FOUND, "Sessão da Pauta [" + id + "] não encontrada.");
	}
}