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

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DesafioVotosApplication.class)
class VotingSessionControllerTest {
	
	@Autowired
    private MockMvc mvc;
	
	private static final String DEFAULT_START_TIME = "2021-01-04T18:00:00";
	
	private static final String DEFAULT_END_TIME = "2021-01-04T20:00:00";
	
	private static final Long DEFAULT_AGENDA_ID = 1L;
	
	private static final String UPDATED_START_TIME = "2021-01-05T18:00:00";
	
	private static final String UPDATED_END_TIME = "2021-01-05T20:00:00";
	
	private static final Long UPDATED_AGENDA_ID = 2L;
	
	@Test
	void findSessionById() throws Exception {		
		mvc.perform(get("/api/v1/voting-session/10"))
				.andExpect(status().isNotFound());		
	}
	
	@Test
	void findAllSessions() throws Exception {		
		mvc.perform(get("/api/v1/voting-session"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));		
	}
	
	@Test
	void createSessionTest() throws Exception {
		mvc.perform(post("/api/v1/voting-session")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(createDefaultSession()))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.startTime").value(equalTo(DEFAULT_START_TIME)))
		.andExpect(jsonPath("$.endTime").value(equalTo(DEFAULT_END_TIME)))
		.andExpect(jsonPath("$.agendaId").value(equalTo(DEFAULT_AGENDA_ID.intValue())));
	}
	
	@Test
	void updateSessionTest() throws Exception {
		mvc.perform(put("/api/v1/voting-session")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(createUpdatedSession()))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.startTime").value(equalTo(UPDATED_START_TIME)))
		.andExpect(jsonPath("$.endTime").value(equalTo(UPDATED_END_TIME)))
		.andExpect(jsonPath("$.agendaId").value(equalTo(UPDATED_AGENDA_ID.intValue())));
	}
	
	@Test
	void updateSessionWithoutIdTest() throws Exception {
		mvc.perform(put("/api/v1/voting-session")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(createUpdatedWithoutIdSession()))
		.andExpect(status().isBadRequest());
	}
	
	private String createDefaultSession() {
		return "{ " + 
				"    \"startTime\": \"" + DEFAULT_START_TIME + "\",\r\n" + 
				"    \"endTime\": \"" + DEFAULT_END_TIME + "\",\r\n" + 
				"    \"agendaId\": " + DEFAULT_AGENDA_ID + "\r\n" + 
				"}";
	}
	
	private String createUpdatedSession() {
		return "{ " + 
				"	 \"id\": 1, " +
				"    \"startTime\": \"" + UPDATED_START_TIME + "\",\r\n" + 
				"    \"endTime\": \"" + UPDATED_END_TIME + "\",\r\n" + 
				"    \"agendaId\": " + UPDATED_AGENDA_ID + "\r\n" + 
				"}";
	}
	
	private String createUpdatedWithoutIdSession() {
		return "{ " + 
				"    \"startTime\": \"" + UPDATED_START_TIME + "\",\r\n" + 
				"    \"endTime\": \"" + UPDATED_END_TIME + "\",\r\n" + 
				"    \"agendaId\": " + UPDATED_AGENDA_ID + "\r\n" + 
				"}";
	}
}
