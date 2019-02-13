package br.com.sicredi.woop.pauta.service;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.sicredi.woop.pauta.exception.WoopPautaNaoLocalizadaException;
import br.com.sicredi.woop.pauta.exception.WoopSessaoAbertaException;
import br.com.sicredi.woop.pauta.exception.WoopSessaoJaIniciadaException;
import br.com.sicredi.woop.pauta.exception.WoopSessaoNaoLocalizadaException;
import br.com.sicredi.woop.pauta.model.Pauta;
import br.com.sicredi.woop.pauta.model.Sessao;
import br.com.sicredi.woop.pauta.repository.SessaoRepository;

@RunWith(MockitoJUnitRunner.class)
public class SessaoServiceTest {

    private static final String PAUTA = "id123Pauta2";

	@InjectMocks
    private SessaoService service;

	@Mock
	private SessaoRepository repository;
	
	@Mock
	private PautaService pautaService;
	
	@Test(expected = WoopPautaNaoLocalizadaException.class)
    public void deveriaIniciarSessaoMasNaoTemPauta() {
    	
    	when(pautaService.buscarPautaPorId(PAUTA))
				.thenReturn(Optional.ofNullable(null));

		service.iniciarSessao(PAUTA, 0);
    }
	
	@Test(expected = WoopPautaNaoLocalizadaException.class)
    public void deveriaBuscarResultadoMasNaoTemPauta() {
    	
    	when(pautaService.buscarPautaPorId(PAUTA))
				.thenReturn(Optional.ofNullable(null));

		service.resultadoVotacaoPauta(PAUTA);
    }
	
	@Test(expected = WoopSessaoJaIniciadaException.class)
    public void deveriaIniciarSessaoMasNaoTemSessao() {
		Pauta pauta = new Pauta("T1", "D1");
		Sessao sessao = new Sessao();
		pauta.setSessao(sessao);
		
    	when(pautaService.buscarPautaPorId(PAUTA))
				.thenReturn(Optional.ofNullable(pauta));

		service.iniciarSessao(PAUTA, 0);
    }
	
	@Test
    public void deveriaIniciarSessaoEConsegue() {
		Pauta pauta = new Pauta("T1", "D1");
		pauta.setSessao(null);
		
    	when(pautaService.buscarPautaPorId(PAUTA))
				.thenReturn(Optional.ofNullable(pauta));
    	
    	when(repository.save(Mockito.any(Sessao.class))).thenReturn(new Sessao(0));

		service.iniciarSessao(PAUTA, 0);
    }
	
	@Test(expected = WoopSessaoNaoLocalizadaException.class)
    public void deveriaCalcularResultadoMasNaoTemSessao() {
		Pauta pauta = new Pauta("T1", "D1");
		pauta.setSessao(null);
		
    	when(pautaService.buscarPautaPorId(PAUTA))
				.thenReturn(Optional.ofNullable(pauta));
    	
		service.resultadoVotacaoPauta(PAUTA);
    }
	
	@Test(expected = WoopSessaoAbertaException.class)
    public void deveriaCalcularResultadoMasSessaoEstaAberta() {
		Pauta pauta = new Pauta("T1", "D1");
		Sessao sessao = new Sessao();
		sessao.setFim(LocalDateTime.now().plusHours(1));
		pauta.setSessao(sessao);
		
    	when(pautaService.buscarPautaPorId(PAUTA))
				.thenReturn(Optional.ofNullable(pauta));
    	
		service.resultadoVotacaoPauta(PAUTA);
    }
	
	@Test
    public void deveriaCalcularResultadoEConsegue() {
		Pauta pauta = new Pauta("T1", "D1");
		Sessao sessao = new Sessao();
		sessao.setFim(LocalDateTime.now().minusHours(1));
		pauta.setSessao(sessao);
		
    	when(pautaService.buscarPautaPorId(PAUTA))
				.thenReturn(Optional.ofNullable(pauta));
    	
		service.resultadoVotacaoPauta(PAUTA);
    }
}