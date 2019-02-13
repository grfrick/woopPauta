package br.com.sicredi.woop.pauta.service;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sicredi.woop.pauta.domain.Resultado;
import br.com.sicredi.woop.pauta.exception.WoopPautaNaoLocalizadaException;
import br.com.sicredi.woop.pauta.exception.WoopSessaoAbertaException;
import br.com.sicredi.woop.pauta.exception.WoopSessaoJaIniciadaException;
import br.com.sicredi.woop.pauta.exception.WoopSessaoNaoLocalizadaException;
import br.com.sicredi.woop.pauta.model.Pauta;
import br.com.sicredi.woop.pauta.model.Sessao;
import br.com.sicredi.woop.pauta.model.Voto;
import br.com.sicredi.woop.pauta.repository.SessaoRepository;

@Service
public class SessaoService {

	@Autowired
    private PautaService pautaService;
	
	@Autowired
    private SessaoRepository repository;

    public Pauta iniciarSessao(String idPauta, long duracao) {
        Pauta pauta = pautaService.buscarPautaPorId(idPauta).orElseThrow(() -> new WoopPautaNaoLocalizadaException());
        
        validaPauta(pauta);
        validaNovaSessao(pauta);
        pauta.setSessao(repository.save(new Sessao(duracao)));

        return pautaService.salvaPauta(pauta);
    }

    public Resultado resultadoVotacaoPauta(String idPauta) {
        Pauta pauta = pautaService.buscarPautaPorId(idPauta).orElseThrow(() -> new WoopPautaNaoLocalizadaException());
        
        validaPauta(pauta);
        validaSessaoEmAndamento(idPauta, pauta);
       
        return contabilizaVotos(pauta.getSessao().getVotos());
    }

    private void validaPauta(Pauta pauta) {
    	if (null == pauta)
    		throw new WoopPautaNaoLocalizadaException();
    }

    private void validaNovaSessao(Pauta pauta) {
    	if (null != pauta.getSessao()) 
    		throw new WoopSessaoJaIniciadaException();
    }

    private void validaSessaoEmAndamento(String idPauta, Pauta pauta) {
    	if (null == pauta.getSessao()) 
    		throw new WoopSessaoNaoLocalizadaException();
    	
    	if (LocalDateTime.now().isBefore(pauta.getSessao().getFim())) 
    		throw new WoopSessaoAbertaException();
    }

    private Resultado contabilizaVotos(Collection<Voto> votos) {
		Resultado apuracao = new Resultado();
		return apuracao.getResultadoCalculado(votos, apuracao);
	}
}