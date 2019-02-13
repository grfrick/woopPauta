package br.com.sicredi.woop.pauta.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.sicredi.woop.pauta.model.Pauta;
import br.com.sicredi.woop.pauta.repository.PautaRepository;
import br.com.sicredi.woop.pauta.service.PautaService;

@RunWith(SpringRunner.class)
@WebMvcTest(PautaController.class)
public class PautaControllerTest {

    private static final String TITULO = "TituloTest";
	private static final String DESCRICAO = "DescTest";

	@Autowired
    private MockMvc mvc;

    @MockBean
    private PautaService service;
    
    @MockBean
    private PautaRepository repository;

    @Test
    public void quandoInformarTodosCamposDeveCriarAssociado() throws Exception {
		mvc.perform(post("/pauta")
                        .contentType(MediaType.APPLICATION_JSON)
				        .param("titulo", TITULO)
				        .param("descricao", DESCRICAO))
                        .andExpect(status().isCreated());
    }

    @Test
    public void quandoConsultarUmaPautaNãoExibeNada() throws Exception {
        mvc.perform(get("/pauta/buscar/123456789")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
    
    @Test
    public void quandoConsultarUmaPautaExibirUnico() throws Exception {
        when(repository.findById(any()))
        .thenReturn(Optional.of(new Pauta()));
        
        when(service.buscarPautaPorId(any()))
		.thenReturn(Optional.of(new Pauta()));

        mvc.perform(get("/pauta/buscar/123456789")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    @Test
    public void quandoListarPautasExibirTodos() throws Exception {
        mvc.perform(get("/pauta/listar")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }
}