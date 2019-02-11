package br.com.sicredi.woop.pauta.service;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.sicredi.woop.pauta.domain.Resultado;
import br.com.sicredi.woop.pauta.enums.SimNaoEnum;
import br.com.sicredi.woop.pauta.exception.PautaWoopException;
import br.com.sicredi.woop.pauta.exception.SessaoWoopException;
import br.com.sicredi.woop.pauta.model.Pauta;
import br.com.sicredi.woop.pauta.model.Sessao;
import br.com.sicredi.woop.pauta.model.Voto;
import br.com.sicredi.woop.pauta.repository.PautaRepository;

@Service
public class SessaoService {

    private static final String EMPATE = "EMPATE";
    
	@Autowired
    private PautaRepository repository;

    public Pauta iniciarSessao(String idPauta, LocalDateTime inicio, LocalDateTime fim) {
        Pauta pauta = repository.findById(idPauta).orElseThrow(() -> new PautaWoopException(HttpStatus.NOT_FOUND, idPauta));
        
        validaPauta(idPauta, pauta);
        pauta.setSessao(new Sessao(inicio, fim));

        return repository.save(pauta);
    }

	private void validaPauta(String idPauta, Pauta pauta) {
		if (null == pauta)
        	throw new PautaWoopException(HttpStatus.NOT_FOUND, "Pauta [" + idPauta + "] não localizada.");
	}

    public Resultado resultadoVotacaoPauta(String idPauta) {
        Pauta pauta = repository.findById(idPauta).orElseThrow(() -> new PautaWoopException(HttpStatus.NOT_FOUND, idPauta));
        validaSessao(idPauta, pauta);
        return contabilizaVotos(pauta.getSessao().getVotos());
    }

	private Resultado contabilizaVotos(Collection<Voto> votos) {
		if (null != votos) {
	         Resultado escrutinio = new Resultado(new Long(votos.size()), 
	        					 				  votos.stream().filter(v -> v.getVoto().compareTo(SimNaoEnum.SIM) == 0).count(), 
	        					 				  votos.stream().filter(v -> v.getVoto().compareTo(SimNaoEnum.NAO) == 0).count());
	         escrutinio.setVencedor(escrutinio.getVotosNao() == escrutinio.getVotosSim() ? EMPATE : 
	        	 					escrutinio.getVotosNao() > escrutinio.getVotosSim() ? SimNaoEnum.NAO.toString() : SimNaoEnum.SIM.toString());
	         
	         return escrutinio;
        } else {
        	return new Resultado(0L, 0L, 0L);
        }
	}

	private void validaSessao(String idPauta, Pauta pauta) {
		if (null == pauta.getSessao()) 
        	throw new SessaoWoopException(HttpStatus.NOT_FOUND, "Sessao não encontrada para a pauta [" + idPauta + "]");
	}
}