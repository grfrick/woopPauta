package br.com.sicredi.woop.pauta.controller;

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

import br.com.sicredi.woop.pauta.enums.SimNaoEnum;
import br.com.sicredi.woop.pauta.service.VotoService;

@RunWith(SpringRunner.class)
@WebMvcTest(VotoController.class)
public class VotoControllerTest {

    private static final String PAUTA = "2PautA33";
	private static final String MATRICULA = "Mae223";

	@Autowired
    private MockMvc mvc;
	
	@MockBean
    private VotoService service;

    @Test
    public void quandoInformarTodosCamposDeveVotar() throws Exception {
		mvc.perform(post("/voto")
                .contentType(MediaType.APPLICATION_JSON)
                .param("idPauta", PAUTA)
		        .param("numeroMatricula", MATRICULA)
				.param("voto", SimNaoEnum.NAO.toString()))
                .andExpect(status().isCreated());
    }
}