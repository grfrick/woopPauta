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
	private static final String MATRICULA = "09481171507";
	private static final String MATRICULA2 = "094811715071";

	@InjectMocks
    private SessaoService service;

	@Mock
	private SessaoRepository repository;
	
	@Mock
	private PautaService pautaService;
	
	
	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

	@Test(expected = WoopPautaNaoLocalizadaException.class)
    public void deveriaIniciarSessaoMasNaoTemPauta() {
    	
    	when(pautaService.buscarPautaPorId(PAUTA))
				.thenReturn(Optional.ofNullable(null));

		service.iniciarSessao(PAUTA, null, null);
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

		service.iniciarSessao(PAUTA, null, null);
    }
	
	@Test
    public void deveriaIniciarSessaoEConsegue() {
		Pauta pauta = new Pauta("T1", "D1");
		pauta.setSessao(null);
		
    	when(pautaService.buscarPautaPorId(PAUTA))
				.thenReturn(Optional.ofNullable(pauta));
    	
    	when(repository.save(Mockito.any(Sessao.class))).thenReturn(new Sessao(null, null));

		service.iniciarSessao(PAUTA, null, null);
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
	
//	@Test(expected = WoopSessaoNaoLocalizadaException.class)
//    public void deveriaVotarMasNaoTemSessao() {
//    	
//    	when(pautaService.buscarPautaPorId(PAUTA))
//				.thenReturn(Optional.of(new Pauta()));
//
//		service.votar(PAUTA, new Voto(MATRICULA, SimNaoEnum.NAO));
//    }
//	
//	@Test(expected = WoopAssociadoNaoLocalizadaException.class)
//    public void deveriaVotarMasNaoAchouAssociado() {
//		Pauta pauta = new Pauta("T1", "D1");
//		Sessao sessao = new Sessao();
//		pauta.setSessao(sessao);
//		
//    	when(pautaService.buscarPautaPorId(PAUTA))
//				.thenReturn(Optional.of(pauta));
//
//		service.votar(PAUTA, new Voto(MATRICULA, SimNaoEnum.NAO));
//    }
//	
//	@Test(expected = WoopSessaoEncerradaException.class)
//    public void deveriaVotarMasSessaoEncerrada() {
//		Pauta pauta = new Pauta("T1", "D1");
//		Sessao sessao = new Sessao();
//		sessao.setFim(LocalDateTime.now().minusHours(1));
//		pauta.setSessao(sessao);
//		
//    	when(pautaService.buscarPautaPorId(PAUTA))
//				.thenReturn(Optional.of(pauta));
//
//		service.votar(PAUTA, new Voto(MATRICULA, SimNaoEnum.NAO));
//    }
//	
//	@Test(expected = WoopVotoJaRealizadoException.class)
//    public void deveriaVotarMasNaoJaVotou() {
//		Pauta pauta = new Pauta("T1", "D1");
//		Sessao sessao = new Sessao();
//		Collection<Voto> votos = new ArrayList<Voto>();
//		Voto voto = new Voto(MATRICULA, SimNaoEnum.NAO);
//		votos.add(voto);
//		sessao.setVotos(votos);
//		pauta.setSessao(sessao);
//		
//    	when(pautaService.buscarPautaPorId(PAUTA))
//				.thenReturn(Optional.of(pauta));
//	
//    	doReturn(new Associado()).when(associadoClient).buscarAssociado(MATRICULA);
//
//		service.votar(PAUTA, new Voto(MATRICULA, SimNaoEnum.NAO));
//    }
//	
//	@Test
//    public void deveriaVotarEConseguiu() {
//		Pauta pauta = new Pauta("T1", "D1");
//		Sessao sessao = new Sessao();
//		Collection<Voto> votos = new ArrayList<Voto>();
//		Voto voto = new Voto(MATRICULA2, SimNaoEnum.NAO);
//		votos.add(voto);
//		sessao.setVotos(votos);
//		pauta.setSessao(sessao);
//		
//    	when(pautaService.buscarPautaPorId(PAUTA))
//				.thenReturn(Optional.of(pauta));
//	
//    	doReturn(new Associado()).when(associadoClient).buscarAssociado(MATRICULA);
//
//		service.votar(PAUTA, new Voto(MATRICULA, SimNaoEnum.NAO));
//    }
}