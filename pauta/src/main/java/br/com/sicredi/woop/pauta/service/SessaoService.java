package br.com.sicredi.woop.pauta.service;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.sicredi.woop.pauta.domain.Resultado;
import br.com.sicredi.woop.pauta.enums.SimNaoEnum;
import br.com.sicredi.woop.pauta.exception.WoopException;
import br.com.sicredi.woop.pauta.model.Pauta;
import br.com.sicredi.woop.pauta.model.Sessao;
import br.com.sicredi.woop.pauta.model.Voto;
import br.com.sicredi.woop.pauta.repository.PautaRepository;
import br.com.sicredi.woop.pauta.repository.SessaoRepository;

@Service
public class SessaoService {

    private static final String EMPATE = "EMPATE";
    
	@Autowired
    private PautaRepository repository;
	
	@Autowired
    private SessaoRepository sessaoRepository;

    public Pauta iniciarSessao(String idPauta, LocalDateTime inicio, LocalDateTime fim) {
        Pauta pauta = repository.findById(idPauta).orElseThrow(() -> new WoopException(HttpStatus.NOT_FOUND, idPauta));
        
        validaPauta(idPauta, pauta);
        pauta.setSessao(sessaoRepository.save(new Sessao(inicio, fim)));

        return repository.save(pauta);
    }

	private void validaPauta(String idPauta, Pauta pauta) {
		if (null == pauta)
        	throw new WoopException(HttpStatus.NOT_FOUND, "Pauta [" + idPauta + "] não localizada.");
		
		if (null != pauta.getSessao()) {
			if (LocalDateTime.now().isBefore(pauta.getSessao().getFim())) 
				throw new WoopException(HttpStatus.UNAUTHORIZED, "A sessão esta aberta, aguarde encerrar.");
		
			if (null != pauta.getSessao().getVotos() && pauta.getSessao().getVotos().size() > 0)
				throw new WoopException(HttpStatus.UNAUTHORIZED, "A sessão esta encerra, e a pauta foi votada. Não é possivel reabrila.");
		}
	}

    public Resultado resultadoVotacaoPauta(String idPauta) {
        Pauta pauta = repository.findById(idPauta).orElseThrow(() -> new WoopException(HttpStatus.NOT_FOUND, idPauta));
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
        	throw new WoopException(HttpStatus.NOT_FOUND, "Sessao não encontrada para a pauta [" + idPauta + "]");
		
		if (LocalDateTime.now().isBefore(pauta.getSessao().getFim())) 
            throw new WoopException(HttpStatus.UNAUTHORIZED, "A sessão está aberta, espere encerrar para ver o resultado final.");
	}
}