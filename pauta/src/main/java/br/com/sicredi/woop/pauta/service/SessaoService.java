package br.com.sicredi.woop.pauta.service;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sicredi.woop.pauta.enums.SimNao;
import br.com.sicredi.woop.pauta.exception.WoopException;
import br.com.sicredi.woop.pauta.model.Pauta;
import br.com.sicredi.woop.pauta.model.Resultado;
import br.com.sicredi.woop.pauta.model.Sessao;
import br.com.sicredi.woop.pauta.model.Voto;
import br.com.sicredi.woop.pauta.repository.PautaRepository;

@Service
public class SessaoService {

    @Autowired
    private PautaRepository repository;

    public Pauta iniciarSessao(String idPauta, LocalDateTime inicio, LocalDateTime fim) {
        Pauta pauta = repository.findById(idPauta).orElseThrow(() -> new WoopException(idPauta));
        
        validaPauta(idPauta, pauta);
        pauta.setSessao(new Sessao(inicio, fim));

        return repository.save(pauta);
    }

	private void validaPauta(String idPauta, Pauta pauta) {
		if (null == pauta)
        	throw new WoopException("Pauta [" + idPauta + "] não localizada.");
	}

    public Resultado resultadoVotacaoPauta(String idPauta) {
        Pauta pauta = repository.findById(idPauta).orElseThrow(() -> new WoopException(idPauta));
        validaSessao(idPauta, pauta);
        return contabilizaVotos(pauta.getSessao().getVotos());
    }

	private Resultado contabilizaVotos(Collection<Voto> votos) {
		if (null != votos) {
	        return new Resultado(new Long(votos.size()), 
	        					 votos.stream().filter(v -> v.getVoto().compareTo(SimNao.SIM) == 0).count(), 
	        					 votos.stream().filter(v -> v.getVoto().compareTo(SimNao.NAO) == 0).count());
        } else {
        	return new Resultado(0L, 0L, 0L);
        }
	}

	private void validaSessao(String idPauta, Pauta pauta) {
		if (null == pauta.getSessao()) 
        	throw new WoopException("Sessao não encontrada para a pauta [" + idPauta + "]");
	}
}