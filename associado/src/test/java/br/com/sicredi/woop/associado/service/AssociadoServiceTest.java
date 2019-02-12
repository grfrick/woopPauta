package br.com.sicredi.woop.associado.service;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.com.sicredi.woop.associado.exception.WoopException;
import br.com.sicredi.woop.associado.model.Associado;
import br.com.sicredi.woop.associado.repository.AssociadoRepository;

@RunWith(MockitoJUnitRunner.class)
public class AssociadoServiceTest {

    private static final String MATRICULA = "09481171507";
	private static final String NOME = "Foo";

	@InjectMocks
    private AssociadoService service;

	@Mock
    private AssociadoRepository repository;

	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void deveCriarAssociado() {
        when(repository.save(any(Associado.class))).thenReturn(new Associado());
        assertThat(service.criar(NOME, MATRICULA), is(IsNull.notNullValue()));
    }
    
    @Test
    public void naoDeveCriarAssociadoDuplicado() {
    	when(repository.findByNumeroMatricula(MATRICULA))
			.thenReturn(new Associado(NOME, MATRICULA));
    	
        try {
        	service.criar(NOME, MATRICULA);
        } catch (WoopException expected) {
        	assertEquals("Já existe um associado com mesma Matricula [09481171507]", expected.getReason());
        }
    }
    
    @Test(expected = WoopException.class)
    public void deveOcorrerErroWoopException() {
        when(repository.save(any(Associado.class))).thenThrow(WoopException.class);
        service.criar(NOME, MATRICULA);
    }
    
    @Test
    public void deveBuscarAssociadoCadastrados() {
    	Page<Associado> paginas = Mockito.mock(Page.class);
    	PageRequest pageRequest = new PageRequest(25, 25);

    	when(repository.findAll(pageRequest))
				.thenReturn(paginas);

		Page<Associado> todos = service.buscarTodosAssociados(pageRequest);
		
		assertEquals(paginas, todos);
    }
    
    @Test
    public void deveBuscarUmAssociadoCadastrados() {
    	Associado associado = new Associado(NOME, MATRICULA); 
    	when(repository.findByNumeroMatricula(MATRICULA))
				.thenReturn(associado);

		Optional<Associado> encontrado = service.buscarAssociado(MATRICULA);
		
		assertEquals(encontrado.get(), associado);
    }
}