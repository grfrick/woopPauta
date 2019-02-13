package br.com.sicredi.woop.pauta.service;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.sicredi.woop.pauta.client.AssociadoClient;
import br.com.sicredi.woop.pauta.domain.Associado;
import br.com.sicredi.woop.pauta.enums.SimNaoEnum;
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

@RunWith(MockitoJUnitRunner.class)
public class VotoServiceTest {

    private static final String PAUTA = "id123Pauta2";
	private static final String MATRICULA = "09481171507";
	private static final String MATRICULA2 = "094811715071";

	@InjectMocks
    private VotoService service;

	@Mock
	private VotoRepository repository;
	
	@Mock
	private PautaService pautaService;
	
	@Mock
	private AssociadoClient associadoClient;
	
	@Test(expected = WoopPautaNaoLocalizadaException.class)
    public void deveriaVotarMasNaoTemPauta() {
    	
    	when(pautaService.buscarPautaPorId(PAUTA))
				.thenReturn(Optional.ofNullable(null));

		service.votar(PAUTA, new Voto(MATRICULA, SimNaoEnum.NAO));
    }
	
	@Test(expected = WoopSessaoNaoLocalizadaException.class)
    public void deveriaVotarMasNaoTemSessao() {
    	
    	when(pautaService.buscarPautaPorId(PAUTA))
				.thenReturn(Optional.of(new Pauta()));

		service.votar(PAUTA, new Voto(MATRICULA, SimNaoEnum.NAO));
    }
	
	@Test(expected = WoopException.class)
    public void deveriaVotarMasNaoAchouAssociado() {
		Pauta pauta = new Pauta("T1", "D1");
		Sessao sessao = new Sessao();
		pauta.setSessao(sessao);
		
    	when(pautaService.buscarPautaPorId(PAUTA))
				.thenReturn(Optional.of(pauta));

		service.votar(PAUTA, new Voto(MATRICULA, SimNaoEnum.NAO));
    }
	
	@Test(expected = WoopSessaoEncerradaException.class)
    public void deveriaVotarMasSessaoEncerrada() {
		Pauta pauta = new Pauta("T1", "D1");
		Sessao sessao = new Sessao();
		sessao.setFim(LocalDateTime.now().minusHours(1));
		pauta.setSessao(sessao);
		
    	when(pautaService.buscarPautaPorId(PAUTA))
				.thenReturn(Optional.of(pauta));

		service.votar(PAUTA, new Voto(MATRICULA, SimNaoEnum.NAO));
    }
	
	@Test(expected = WoopVotoJaRealizadoException.class)
    public void deveriaVotarMasNaoJaVotou() {
		Pauta pauta = new Pauta("T1", "D1");
		Sessao sessao = new Sessao();
		Collection<Voto> votos = new ArrayList<Voto>();
		Voto voto = new Voto(MATRICULA, SimNaoEnum.NAO);
		votos.add(voto);
		sessao.setVotos(votos);
		pauta.setSessao(sessao);
		
    	when(pautaService.buscarPautaPorId(PAUTA))
				.thenReturn(Optional.of(pauta));
	
    	doReturn(new Associado()).when(associadoClient).buscarAssociado(MATRICULA);

		service.votar(PAUTA, new Voto(MATRICULA, SimNaoEnum.NAO));
    }
	
	@Test
    public void deveriaVotarEConseguiu() {
		Pauta pauta = new Pauta("T1", "D1");
		Sessao sessao = new Sessao();
		Collection<Voto> votos = new ArrayList<Voto>();
		Voto voto = new Voto(MATRICULA2, SimNaoEnum.NAO);
		votos.add(voto);
		sessao.setVotos(votos);
		pauta.setSessao(sessao);
		
    	when(pautaService.buscarPautaPorId(PAUTA))
				.thenReturn(Optional.of(pauta));
	
    	doReturn(new Associado()).when(associadoClient).buscarAssociado(MATRICULA);

		service.votar(PAUTA, new Voto(MATRICULA, SimNaoEnum.NAO));
    }
	
	@Test(expected = WoopAssociadoNaoLocalizadaException.class)
    public void deveriaVotarMasNaoEncontrouAssociado() {
		Pauta pauta = new Pauta("T1", "D1");
		Sessao sessao = new Sessao();
		Collection<Voto> votos = new ArrayList<Voto>();
		Voto voto = new Voto(MATRICULA2, SimNaoEnum.NAO);
		votos.add(voto);
		sessao.setVotos(votos);
		pauta.setSessao(sessao);
		
    	when(pautaService.buscarPautaPorId(PAUTA))
				.thenReturn(Optional.of(pauta));
	
    	doThrow(FeignException.class).when(associadoClient).buscarAssociado(MATRICULA);
    	
    	
		service.votar(PAUTA, new Voto(MATRICULA, SimNaoEnum.SIM));
    }
	
	@Test(expected = WoopAssociadoForaDoArException.class)
    public void deveriaVotarMasServicoDeAssociadoForaDoAr() {
		Pauta pauta = new Pauta("T1", "D1");
		Sessao sessao = new Sessao();
		Collection<Voto> votos = new ArrayList<Voto>();
		Voto voto = new Voto(MATRICULA2, SimNaoEnum.NAO);
		votos.add(voto);
		sessao.setVotos(votos);
		pauta.setSessao(sessao);
		
    	when(pautaService.buscarPautaPorId(PAUTA))
				.thenReturn(Optional.of(pauta));
	
    	doThrow(RetryableException.class).when(associadoClient).buscarAssociado(MATRICULA);
    	
    	
		service.votar(PAUTA, new Voto(MATRICULA, SimNaoEnum.SIM));
    }
}