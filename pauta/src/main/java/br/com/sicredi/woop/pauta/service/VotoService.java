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
import br.com.sicredi.woop.pauta.exception.WoopException;
import br.com.sicredi.woop.pauta.exception.WoopPautaNaoLocalizadaException;
import br.com.sicredi.woop.pauta.exception.WoopSessaoEncerradaException;
import br.com.sicredi.woop.pauta.exception.WoopSessaoNaoLocalizadaException;
import br.com.sicredi.woop.pauta.exception.WoopVotoJaRealizadoException;
import br.com.sicredi.woop.pauta.model.Pauta;
import br.com.sicredi.woop.pauta.model.Sessao;
import br.com.sicredi.woop.pauta.model.Voto;
import br.com.sicredi.woop.pauta.repository.VotoRepository;
import feign.FeignException;
import feign.RetryableException;

@Service
public class VotoService {
	
	@Autowired
	public VotoRepository repository;
	
	@Autowired
	public PautaService pautaService;
	
	@Autowired
	public AssociadoClient AssociadoClient;

	public Pauta votar(String idPauta, Voto voto) {
		Pauta pauta = pautaService.buscarPautaPorId(idPauta).orElseThrow(() -> new WoopPautaNaoLocalizadaException());

        validaSessao(pauta.getSessao());
        validaPeriodoVotacao(pauta);        
        validaAssociadoValido(voto.getNumeroMatricula());
        validaVotoRepetido(voto.getNumeroMatricula(), pauta.getSessao().getVotos());
        
        pauta.getSessao().getVotos().add(repository.save(voto));
        
        return pautaService.salvaPauta(pauta);
    }
	
	private void validaSessao(Sessao sessao) {
		if (null == sessao) 
			throw new WoopSessaoNaoLocalizadaException();
	}

	private void validaPeriodoVotacao(Pauta pauta) {
		if (LocalDateTime.now().isAfter(pauta.getSessao().getFim())) 
			throw new WoopSessaoEncerradaException();
	}

	private void validaAssociadoValido(String tituloAssociado) {
		Associado associado = null;
		
		try {
			associado = AssociadoClient.buscarAssociado(tituloAssociado);
		} catch (RetryableException erroOff) {
			throw new WoopAssociadoForaDoArException();
		} catch (FeignException erroUsuario) {
			throw new WoopAssociadoNaoLocalizadaException();
		} catch (RuntimeException erro) {
			throw new WoopException();
		}
		
		if (null == associado) 
			throw new WoopException();
	}


	private void validaVotoRepetido(String numeroMatricula, Collection<Voto> votos) {
		Optional<Voto> temVoto = votos.stream()
							            .filter(v -> v.getNumeroMatricula().equals(numeroMatricula))
							            .findFirst();

        if (temVoto.isPresent()) 
        	throw new WoopVotoJaRealizadoException();
	}
}