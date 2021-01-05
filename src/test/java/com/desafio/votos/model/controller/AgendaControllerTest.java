package com.desafio.votos.model.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;

import com.desafio.votos.DesafioVotosApplication;
import com.desafio.votos.model.dto.AgendaDTO;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DesafioVotosApplication.class)
class AgendaControllerTest {
	
	private static final String DEFAULT_DESCRIPTION = "DEFAULT DESCRIPTION";
	
	private static final String DEFAULT_AGENDA_NAME = "DEFAULT AGENDA";
	
	private static final String UPDATED_DESCRIPTION = "UPDATED DESCRIPTION";
	
	private static final String UPDATED_AGENDA_NAME = "UPDATED AGENDA";
	
	@Autowired
    private MockMvc mvc;

	@Test
	void getAgendaNotFoundTest() throws Exception {
		mvc.perform(get("/api/v1/agenda/10"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	void getAgendaTest() throws Exception {
		mvc.perform(get("/api/v1/agenda/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.agendaName").value(equalTo("TEST AGENDA")))
		.andExpect(jsonPath("$.agendaDescription").value(equalTo("TEST AGENDA DESCRIPTION")));
	}
	
	@Test
	void createAgendaTest() throws Exception {
		mvc.perform(post("/api/v1/agenda")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(createDefaultAgenda().toJson()))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.agendaName").value(equalTo(DEFAULT_AGENDA_NAME)))
		.andExpect(jsonPath("$.agendaDescription").value(equalTo(DEFAULT_DESCRIPTION)));
	}
	
	@Test
	void updateAgendaTest() throws Exception {
		AgendaDTO agenda = createUpdatedAgenda(1L);
		
		mvc.perform(put("/api/v1/agenda")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(agenda.toJson()))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.agendaName").value(equalTo(UPDATED_AGENDA_NAME)))
		.andExpect(jsonPath("$.agendaDescription").value(equalTo(UPDATED_DESCRIPTION)));
	}
	
	@Test
	void updateAgendaTestWithoutId() throws Exception {
		mvc.perform(put("/api/v1/agenda")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(createDefaultAgenda().toJson()))
		.andExpect(status().isBadRequest());
	}
	
	private AgendaDTO createDefaultAgenda() {
		AgendaDTO agenda = new AgendaDTO();
		
		agenda.setAgendaDescription(DEFAULT_DESCRIPTION);
		agenda.setAgendaName(DEFAULT_AGENDA_NAME);
		
		return agenda;
	}
	
	private AgendaDTO createUpdatedAgenda(Long id) {
		AgendaDTO agenda = new AgendaDTO();
		
		agenda.setAgendaDescription(UPDATED_DESCRIPTION);
		agenda.setAgendaName(UPDATED_AGENDA_NAME);
		
		agenda.setId(id);
		
		return agenda;
	}

}
