package com.desafio.votos.model.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.desafio.votos.DesafioVotosApplication;
import com.desafio.votos.model.dto.VoteTypeDTO;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DesafioVotosApplication.class)
class VoteTypeControllerTest {

	@Autowired
    private MockMvc mvc;
	
	private static final Long DEFAULT_AGENDA = 1L;
	
	private static final String DEFAULT_DESCRIPTION = "DEFAULT DESCRIPTION";
	
	private static final Long UPDATED_AGENDA = 2L;
	
	private static final String UPDATED_DESCRIPTION = "UPDATED DESCRIPTION";
	
	@Test
	void findVoteTypeById() throws Exception {		
		mvc.perform(get("/api/v1/vote-type/10"))
				.andExpect(status().isNotFound());		
	}
	
	@Test
	void findAllAgendaVoteType() throws Exception {		
		mvc.perform(get("/api/v1/vote-type/agenda/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));		
	}
	
	@Test
	void createVoteTypeTest() throws Exception {
		mvc.perform(post("/api/v1/vote-type")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(createDefaultVoteType().toJson()))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.voteDescription").value(equalTo(DEFAULT_DESCRIPTION)))
		.andExpect(jsonPath("$.agendaId").value(equalTo(DEFAULT_AGENDA.intValue())));
	}
	
	@Test
	void updateVoteTypeTest() throws Exception {
		mvc.perform(put("/api/v1/vote-type")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(createUpdatedVoteType(1L).toJson()))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.voteDescription").value(equalTo(UPDATED_DESCRIPTION)))
		.andExpect(jsonPath("$.agendaId").value(equalTo(UPDATED_AGENDA.intValue())));
	}
	
	@Test
	void updateVoteTypeWithoutIdTest() throws Exception {
		mvc.perform(put("/api/v1/voting-session")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(createDefaultVoteType().toJson()))
		.andExpect(status().isBadRequest());
	}
	
	private VoteTypeDTO createDefaultVoteType() {
		VoteTypeDTO voteType = new VoteTypeDTO();
		
		voteType.setAgendaId(DEFAULT_AGENDA);
		voteType.setVoteDescription(DEFAULT_DESCRIPTION);
		
		return voteType;
	}
	
	private VoteTypeDTO createUpdatedVoteType(Long id) {
		VoteTypeDTO voteType = new VoteTypeDTO();
		
		voteType.setId(id);
		voteType.setAgendaId(UPDATED_AGENDA);
		voteType.setVoteDescription(UPDATED_DESCRIPTION);
		
		return voteType;
	}

}
