package com.desafio.votos.model.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import com.desafio.votos.DesafioVotosApplication;
import com.desafio.votos.enumeration.VoteStatusEnum;
import com.desafio.votos.model.dto.AssociateDTO;
import com.desafio.votos.model.dto.PersonStatusDTO;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DesafioVotosApplication.class)
@TestMethodOrder(OrderAnnotation.class)
class AssociateControllerTest {

	@Autowired
    private MockMvc mvc;
	
	@MockBean
	private RestTemplate restTemplate;
	
	@Value("${person.validation.host}")
	private String validationHost;
	
	@Value("${person.validation.path}")
	private String validationPath;
	
	private static final String DEFAULT_CPF = "29076242020";
	
	private static final String DEFAULT_NAME = "JOSÉ";
	
	private static final String UPDATED_NAME = "JOSÉ DA SILVA";

	@Test
	@Order(1)
	void findUnknownAssociateByIdTest() throws Exception {		
		mvc.perform(get("/api/v1/associate/10"))
				.andExpect(status().isNotFound());		
	}
	
	@Test
	@Order(2)
	void findAssociateByIdTest() throws Exception {		
		mvc.perform(get("/api/v1/associate/09854960021"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.cpf").value(equalTo("***549600**")))
			.andExpect(jsonPath("$.name").value(equalTo("JOAO")));;		
	}
	
	@Test
	@Order(3)
	void findAllAssociatesTest() throws Exception {		
		mvc.perform(get("/api/v1/associate"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(3)));		
	}
	
	@Test
	@Order(4)
	void createAssociateTest() throws Exception {		
		
		Mockito
        .when(restTemplate.getForEntity(
        		validationHost + validationPath + DEFAULT_CPF, PersonStatusDTO.class))
        .thenReturn(new ResponseEntity<PersonStatusDTO>(createPersonStatus(), HttpStatus.OK));
		
		mvc.perform(post("/api/v1/associate")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(createDefaultAssociate().toJson()))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.cpf").value(equalTo("***762420**")))
		.andExpect(jsonPath("$.name").value(equalTo(DEFAULT_NAME)));
	}
	
	@Test
	@Order(5)
	void updateAssociateTest() throws Exception {
		
		Mockito
        .when(restTemplate.getForEntity(
        		validationHost + validationPath + DEFAULT_CPF, PersonStatusDTO.class))
        .thenReturn(new ResponseEntity<PersonStatusDTO>(createPersonStatus(), HttpStatus.OK));
		
		mvc.perform(put("/api/v1/associate")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(createUpdatedAssociate().toJson()))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.cpf").value(equalTo("***762420**")))
		.andExpect(jsonPath("$.name").value(equalTo(UPDATED_NAME)));
	}
	
	private AssociateDTO createDefaultAssociate() {
		AssociateDTO associate = new AssociateDTO();
		
		associate.setCpf(DEFAULT_CPF);
		associate.setName(DEFAULT_NAME);
		
		return associate;
	}
	
	private AssociateDTO createUpdatedAssociate() {
		AssociateDTO associate = new AssociateDTO();
		
		associate.setCpf(DEFAULT_CPF);
		associate.setName(UPDATED_NAME);
		
		return associate;
	}
	
	private PersonStatusDTO createPersonStatus() {
		PersonStatusDTO personStatus = new PersonStatusDTO();
		
		personStatus.setStatus(VoteStatusEnum.ABLE_TO_VOTE.status());
		
		return personStatus;
	}

}
