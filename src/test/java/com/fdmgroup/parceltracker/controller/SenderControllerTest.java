package com.fdmgroup.parceltracker.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.parceltracker.model.Parcel;
import com.fdmgroup.parceltracker.model.Sender;
import com.fdmgroup.parceltracker.service.SenderServiceImp;

@WebMvcTest(SenderController.class)
class SenderControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private SenderServiceImp mockSenderService;

	private Sender sender;

	private Parcel parcel1;
	private Parcel parcel2;

	@BeforeEach
	void setUp() throws Exception {

		sender = new Sender();
		sender.setId(1L);
		sender.setName("John Doe");
		sender.setEmail("john.doe@mail.com");
		sender.setAddress("101 Queen Street LE1 64LD London");

		parcel1 = new Parcel();
		parcel1.setId(1L);
		parcel1.setDeliveryStatus("Preparing");
		parcel1.setDimensions("10x10x10");
		parcel1.setWeight(10.0);
		parcel1.setSender(null);
		parcel1.setSender(null);

		parcel2 = new Parcel();
		List<Parcel> parcels = new ArrayList<>();
		parcels.add(parcel1);
		parcels.add(parcel2);
		sender.setParcelsSent(parcels);
	}

	@Test
	@DisplayName("Save Sender - Positive")
	void arrangeSenderObject_actSaveSender_assertReturnSavedSender() throws JsonProcessingException, Exception {
		// arrange
		// Arrange

		given(mockSenderService.createSender(ArgumentMatchers.any(Sender.class)))

				.willAnswer(invocation -> invocation.getArgument(0));

		// Act - Assert

		// @formatter:off

        mockMvc.perform(post("/api/v1/senders")

                .contentType(MediaType.APPLICATION_JSON)

                .content(objectMapper.writeValueAsString(sender)))

                .andDo(print())

                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.name", is(sender.getName())))

                .andExpect(jsonPath("$.email", is(sender.getEmail())))

                .andExpect(jsonPath("$.address", is(sender.getAddress())))
        
		        .andExpect(jsonPath("$.parcelsSent[0].dimensions", is(parcel1.getDimensions())));

             

        // @formatter:on

		verify(mockSenderService, times(1)).createSender(ArgumentMatchers.any(Sender.class));
		// act
		// assert
	}

	@Test
	@DisplayName("Save Sender - Negative")
	void arrangeSenderObject_actSaveSender_assertReturnError() throws JsonProcessingException, Exception {
		// arrange
		// Arrange
		sender.setName(null);
		given(mockSenderService.createSender(ArgumentMatchers.any(Sender.class)))

				.willAnswer(invocation -> invocation.getArgument(0));

		// Act - Assert

		// @formatter:off

        mockMvc.perform(post("/api/v1/senders")

                .contentType(MediaType.APPLICATION_JSON)

                .content(objectMapper.writeValueAsString(sender)))

                .andDo(print())

                .andExpect(status().isBadRequest());

               
             

        // @formatter:on

		verify(mockSenderService, times(0)).createSender(ArgumentMatchers.any(Sender.class));
		// act
		// assert
	}

	@Test
	@DisplayName("findSenderByID--positive")
	void givenSenderId_whenFindSenderById_thenReturnSenderObjectFromDB() throws Exception {
		// arrange
		given(mockSenderService.getSenderById(1L)).willReturn(sender);
		// act-assert

		// @Formatter: off
		mockMvc.perform(get("/api/v1/senders/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(sender.getName())))

				.andExpect(jsonPath("$.email", is(sender.getEmail())))

				.andExpect(jsonPath("$.address", is(sender.getAddress())))
				.andExpect(jsonPath("$.parcelsSent[0].dimensions", is(parcel1.getDimensions())));

		// @Formatter: on

		verify(mockSenderService, times(1)).getSenderById(1L);

	}

	@Test
	@DisplayName("findSenderByID--negative")
	void givenSenderId_whenFindSenderById_thenReturnError() throws Exception {
		// arrange
		given(mockSenderService.getSenderById(2L)).willReturn(null);
		// act-assert

		// @Formatter: off
		mockMvc.perform(get("/api/v1/senders/2")).andDo(print()).andExpect(status().isNotFound());

		// @Formatter: on

		verify(mockSenderService, times(1)).getSenderById(2L);

	}

	@Test
	@DisplayName("findSenderByEmail--positive")
	void givenSenderEmail_whenFindSenderByEmail_thenReturnSenderObjectFromDB() throws Exception {
		List<Sender> senders = new ArrayList<>();
		senders.add(sender);
		String email = "john.doe@mail.com";
		// arrange
		given(mockSenderService.getSenderByEmail(email)).willReturn(senders);
		// act-assert

		// @Formatter: off
		mockMvc.perform(get("/api/v1/senders/email/" + email)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))

				.andExpect(jsonPath("$[0].name", is(sender.getName())))

				.andExpect(jsonPath("$[0].email", is(sender.getEmail())))

				.andExpect(jsonPath("$[0].address", is(sender.getAddress())))

				.andExpect(jsonPath("$[0].parcelsSent[0].dimensions", is(parcel1.getDimensions())));

		// @Formatter: on

		verify(mockSenderService, times(1)).getSenderByEmail(email);

	}

	@Test
	@DisplayName("findSenderByEmail--negative")
	void givenSenderEmail_whenFindSenderByEmail_thenReturnError() throws Exception {
		List<Sender> emptySenders = new ArrayList<>();

		String email = "fake@mail.com";
		// arrange
		given(mockSenderService.getSenderByEmail(email)).willReturn(emptySenders);
		// act-assert

		// @Formatter: off
		mockMvc.perform(get("/api/v1/senders/email/" + email))

				.andDo(print()).andExpect(status().isNotFound());

		// @Formatter: on

		verify(mockSenderService, times(1)).getSenderByEmail(email);

	}

	@Test
	@DisplayName("findAllSenders")
	public void givenNothing_whenFindAllSenders_thenReturnAllSenders() throws Exception {
		// act
		List<Sender> senders = List.of(sender);
		when(mockSenderService.getAllSenders()).thenReturn(senders);
		// act-assert
		mockMvc.perform(get("/api/v1/senders")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))

				.andExpect(jsonPath("$[0].name", is(sender.getName())))

				.andExpect(jsonPath("$[0].email", is(sender.getEmail())))

				.andExpect(jsonPath("$[0].address", is(sender.getAddress())))

				.andExpect(jsonPath("$[0].parcelsSent[0].dimensions", is(parcel1.getDimensions())));

		verify(mockSenderService, times(1)).getAllSenders();
	}

	@AfterEach
	void tearDown() throws Exception {

		sender = null;
		parcel1 = null;
		parcel2 = null;
	}

}
