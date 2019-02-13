package br.com.sicredi.woop.pauta.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.com.sicredi.woop.pauta.model.Pauta;
import br.com.sicredi.woop.pauta.repository.PautaRepository;

@RunWith(MockitoJUnitRunner.class)
public class PautaServiceTest {

    private static final String DESCRICAO = "D1";
	private static final String TEMA = "T1";
	private static final String PAUTA = "id123Pauta2";

	@InjectMocks
    private PautaService service;

	@Mock
	private PautaRepository repository;
	
	
	@Test
    public void deveriaCriarUmaPautaSessaoEConsegue() {
    	when(repository.save(Mockito.any(Pauta.class))).thenReturn(new Pauta(TEMA, DESCRICAO));
		service.criarPauta(PAUTA, DESCRICAO);
    }
	
	@Test
    public void deveriaSalvarUmaPautaSessaoEConsegue() {
		Pauta pauta = new Pauta(TEMA, DESCRICAO);
    	when(repository.save(Mockito.any(Pauta.class))).thenReturn(new Pauta(TEMA, DESCRICAO));
		service.salvaPauta(pauta);
    }
	
    @Test
    public void deveBuscarUmaPautaCadastrada() {
    	Pauta pauta = new Pauta(TEMA, DESCRICAO); 
    	when(repository.findById(PAUTA))
				.thenReturn(Optional.of(pauta));

		Optional<Pauta> encontrado = service.buscarPautaPorId(PAUTA);
		
		assertEquals(encontrado.get(), pauta);
    }
	
    @Test
    public void deveBuscarPautasCadastradss() {
    	Page<Pauta> paginas = Mockito.mock(Page.class);
    	PageRequest pageRequest = new PageRequest(25, 25);

    	when(repository.findAll(pageRequest))
				.thenReturn(paginas);

		Page<Pauta> todos = service.buscarTodasPautas(pageRequest);
		
		assertEquals(paginas, todos);
    }
}