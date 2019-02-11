package br.com.sicredi.woop.pauta.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sicredi.woop.pauta.enums.SimNao;
import br.com.sicredi.woop.pauta.exception.WoopException;
import br.com.sicredi.woop.pauta.model.Pauta;
import br.com.sicredi.woop.pauta.model.Resultado;
import br.com.sicredi.woop.pauta.model.Sessao;
import br.com.sicredi.woop.pauta.repository.PautaRepository;

@Service
public class SessaoService {

    @Autowired
    private PautaRepository repository;

    public Pauta iniciarSessao(String idPauta, LocalDateTime inicio, LocalDateTime fim) {
        Pauta pauta = repository.findById(idPauta).orElseThrow(() -> new WoopException(idPauta));
        
        if (null == pauta)
        	throw new WoopException("Pauta [" + idPauta + "] não localizada.");
        
        pauta.setSessao(new Sessao(inicio, fim));
        return repository.save(pauta);
    }

    public Resultado resultadoVotacaoPauta(String idPauta) {
        Pauta pauta = repository.findById(idPauta).orElseThrow(() -> new WoopException(idPauta));

        if (null == pauta.getSessao()) 
        	throw new WoopException("Sessao não encontrada para a pauta [" + idPauta + "]");
        
        
        if (null != pauta.getSessao().getVotos()) {
	        return new Resultado(new Long(pauta.getSessao().getVotos().size()), 
	        					 pauta.getSessao().getVotos().stream().filter(voto -> voto.getVoto().compareTo(SimNao.SIM) == 0).count(), 
	        					 pauta.getSessao().getVotos().stream().filter(voto -> voto.getVoto().compareTo(SimNao.NAO) == 0).count());
        }
        
        return new Resultado(0L, 0L, 0L);
    }
}