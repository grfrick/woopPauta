package br.com.sicredi.woop.pauta.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sicredi.woop.pauta.client.AssociadoClient;
import br.com.sicredi.woop.pauta.domain.Associado;
import br.com.sicredi.woop.pauta.exception.WoopAssociadoForaDoArException;
import br.com.sicredi.woop.pauta.exception.WoopAssociadoNaoLocalizadaException;
import br.com.sicredi.woop.pauta.exception.WoopPautaNaoLocalizadaException;
import br.com.sicredi.woop.pauta.exception.WoopSessaoEncerradaException;
import br.com.sicredi.woop.pauta.exception.WoopSessaoNaoLocalizadaException;
import br.com.sicredi.woop.pauta.exception.WoopVotoJaRealizadoException;
import br.com.sicredi.woop.pauta.model.Pauta;
import br.com.sicredi.woop.pauta.model.Sessao;
import br.com.sicredi.woop.pauta.model.Voto;
import br.com.sicredi.woop.pauta.repository.VotoRepository;

@Service
public class VotoService {
	
	@Autowired
	public PautaService pautaService;
	
	@Autowired
	public VotoRepository repository;
	
	@Autowired
	public AssociadoClient AssociadoClient;

	public Pauta votar(String idPauta, Voto voto) {
		Pauta pauta = pautaService.buscarPautaPorId(idPauta).orElseThrow(() -> new WoopPautaNaoLocalizadaException());

        validaPauta(idPauta, pauta.getSessao());
        validaPeriodoVotacao(pauta);        
        validaAssociadoValido(voto.getNumeroMatricula());
        validaVotoRepetido(idPauta, voto.getNumeroMatricula(), pauta.getSessao().getVotos());
        
        pauta.getSessao().getVotos().add(repository.save(voto));
        
        return pautaService.salvaPauta(pauta);
    }

	private void validaAssociadoValido(String tituloAssociado) {
		Associado associado = null;
		
		try {
			associado = AssociadoClient.buscarAssociado(tituloAssociado);
		} catch (RuntimeException erro) {
			throw new WoopAssociadoForaDoArException();
		}
		
		if (null == associado) 
			throw new WoopAssociadoNaoLocalizadaException();
	}

	private void validaPeriodoVotacao(Pauta pauta) {
		if (LocalDateTime.now().isAfter(pauta.getSessao().getFim())) 
            throw new WoopSessaoEncerradaException();
	}

	private void validaVotoRepetido(String id, String numeroMatricula, Collection<Voto> votos) {
		Optional<Voto> temVoto = votos.stream()
							            .filter(v -> v.getNumeroMatricula().equals(numeroMatricula))
							            .findFirst();

        if (temVoto.isPresent()) 
        	throw new WoopVotoJaRealizadoException();
	}

	private void validaPauta(String id, Sessao sessao) {
		if (null == sessao) 
        	throw new WoopSessaoNaoLocalizadaException();
	}
}