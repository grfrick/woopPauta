package br.com.sicredi.woop.associado.controller;

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

import br.com.sicredi.woop.associado.model.Associado;
import br.com.sicredi.woop.associado.repository.AssociadoRepository;
import br.com.sicredi.woop.associado.service.AssociadoService;

@RunWith(SpringRunner.class)
@WebMvcTest(AssociadoController.class)
public class AssociadoControllerTest {

    private static final String MATRICULA = "123456780";
	private static final String NOME = "Gian";

	@Autowired
    private MockMvc mvc;

    @MockBean
    private AssociadoService service;

    @MockBean
    private AssociadoRepository repository;
    
    @Test
    public void quandoInformarTodosCamposDeveCriarAssociado() throws Exception {
		mvc.perform(post("/associado")
                        .contentType(MediaType.APPLICATION_JSON)
				        .param("nome", NOME)
				        .param("numeroMatricula", MATRICULA))
                        .andExpect(status().isCreated());
    }

    @Test
    public void quandoNãoInformarNumeroMatriculaNaoDeveCriarAssociado() throws Exception {
        mvc.perform(post("/associado")
                        .contentType(MediaType.APPLICATION_JSON)
				        .param("nome", NOME))
                        .andExpect(status().isBadRequest());
    }
    
    @Test
    public void quandoNãoInformarNomeNaoDeveCriarAssociado() throws Exception {
        mvc.perform(post("/associado")
                        .contentType(MediaType.APPLICATION_JSON)
				        .param("numeroMatricula", MATRICULA))
                        .andExpect(status().isBadRequest());
    }

    @Test
    public void quandoListarAssociadoesExibirTodos() throws Exception {
        mvc.perform(get("/associado/listar")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

    @Test
    public void quandoConsultarUmAssociadoNãoExibeNada() throws Exception {
        mvc.perform(get("/associado/buscar/123456789")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void quandoConsultarUmAssociadoExibirUnico() throws Exception {
        when(repository.findByNumeroMatricula(any()))
                        .thenReturn(new Associado());
        when(service.buscarAssociado(any()))
        				.thenReturn(Optional.of(new Associado()));

        mvc.perform(get("/associado/buscar/123456789")
                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }
}