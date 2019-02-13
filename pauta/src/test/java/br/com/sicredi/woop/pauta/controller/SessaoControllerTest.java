package br.com.sicredi.woop.pauta.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.sicredi.woop.pauta.service.SessaoService;

@RunWith(SpringRunner.class)
@WebMvcTest(SessaoController.class)
public class SessaoControllerTest {

	private static final String PAUTA = "2PauTA33";

	@Autowired
    private MockMvc mvc;

    @MockBean
    private SessaoService service;
    
	@Test
    public void quandoInformarTodosCamposIniciarSessao() throws Exception {
		mvc.perform(post("/sessao/iniciarSessao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("idPauta", PAUTA)
				        .param("inicio", "")
				        .param("fim", ""))
                        .andExpect(status().isCreated());
	}
	
    @Test
    public void quandoInformarUmaPautaBuscarResultado() throws Exception {
        mvc.perform(get("/sessao/resultado")
                .contentType(MediaType.APPLICATION_JSON)
                .param("idPauta", PAUTA))
                .andExpect(status().isOk());
    }
}