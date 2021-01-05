package com.desafio.votos.model.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import com.desafio.votos.DesafioVotosApplication;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DesafioVotosApplication.class)
class VoteControllerTest {
	
	@Autowired
    private MockMvc mvc;
	
	@MockBean
	private RestTemplate restTemplate;
	
	@Value("${person.validation.host}")
	private String validationHost;
	
	@Value("${person.validation.path}")
	private String validationPath;
	
//	private static final Long DEFAULT_AGENDA = 1L;
//	
//	private static final String DEFAULT_ASSOCIATE_CPF = "02914457057";
//	
//	private static final Long DEFAULT_VOTE_TYPE = 2L;
//	
//	private static final Long DEFAULT_VOTING_SESSION = 1L;

	@Test
	void findUnknownVoteByIdTest() throws Exception {		
		mvc.perform(get("/api/v1/vote/10"))
				.andExpect(status().isNotFound());		
	}
	
	@Test
	void findVoteByIdTest() throws Exception {		
		mvc.perform(get("/api/v1/vote/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(equalTo(1)))
				.andExpect(jsonPath("$.votingDate").value(equalTo("2021-01-04T08:11:45")))
				.andExpect(jsonPath("$.voteType.id").value(equalTo(1)))
				.andExpect(jsonPath("$.voteType.voteDescription").value(equalTo("YES")))
				.andExpect(jsonPath("$.voteType.agendaId").value(equalTo(1)));
	}
	
	@Test
	void findAllAgendaVoteTest() throws Exception {		
		mvc.perform(get("/api/v1/vote/agenda/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));		
	}
	
//	@Test
//	void votegfdgdfpuoigheruioTest() throws Exception {		
//		
//		Mockito
//        .when(restTemplate.getForEntity(
//        		validationHost + validationPath + DEFAULT_ASSOCIATE_CPF, PersonStatusDTO.class))
//        .thenReturn(new ResponseEntity<PersonStatusDTO>(createPersonStatus(), HttpStatus.OK));
//		
//		mvc.perform(post("/api/v1/vote")
//				.contentType(MediaType.APPLICATION_JSON_VALUE)
//				.content(createDefaultVote().toJson()))
//		.andExpect(status().isCreated());
//	}
//	
//	private VotingDTO createDefaultVote() {
//		VotingDTO vote = new VotingDTO();
//		
//		vote.setAgendaId(DEFAULT_AGENDA);
//		vote.setAssociateCPF(DEFAULT_ASSOCIATE_CPF);
//		vote.setVoteTypeId(DEFAULT_VOTE_TYPE);
//		vote.setVotingSessionId(DEFAULT_VOTING_SESSION);
//		
//		return vote;
//	}
//	
//	private PersonStatusDTO createPersonStatus() {
//		PersonStatusDTO personStatus = new PersonStatusDTO();
//		
//		personStatus.setStatus(VoteStatusEnum.ABLE_TO_VOTE.status());
//		
//		return personStatus;
//	}

}
