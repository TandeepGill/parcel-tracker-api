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
import com.fdmgroup.parceltracker.model.Receiver;
import com.fdmgroup.parceltracker.service.ReceiverServiceImp;

@WebMvcTest(ReceiverController.class)
class ReceiverControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ReceiverServiceImp mockReceiverService;

	private Receiver receiver;

	private Parcel parcel1;
	private Parcel parcel2;

	@BeforeEach
	void setUp() throws Exception {

		receiver = new Receiver();
		receiver.setId(1L);
		receiver.setName("John Doe");
		receiver.setEmail("john.doe@mail.com");
		receiver.setAddress("101 Queen Street LE1 64LD London");

		parcel1 = new Parcel();
		parcel1.setId(1L);
		parcel1.setDeliveryStatus("Preparing");
		parcel1.setDimensions("10x10x10");
		parcel1.setWeight(10.0);
		parcel1.setSender(null);
		parcel1.setReceiver(null);

		parcel2 = new Parcel();
		List<Parcel> parcels = new ArrayList<>();
		parcels.add(parcel1);
		parcels.add(parcel2);
		receiver.setParcelsReceived(parcels);
	}

	@Test
	@DisplayName("Save Receiver - Positive")
	void arrangeReceiverObject_actSaveReceiver_assertReturnSavedReceiver() throws JsonProcessingException, Exception {
		// arrange
		// Arrange

		given(mockReceiverService.createReceiver(ArgumentMatchers.any(Receiver.class)))

				.willAnswer(invocation -> invocation.getArgument(0));

		// Act - Assert

		// @formatter:off

        mockMvc.perform(post("/api/v1/receivers")

                .contentType(MediaType.APPLICATION_JSON)

                .content(objectMapper.writeValueAsString(receiver)))

                .andDo(print())

                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.name", is(receiver.getName())))

                .andExpect(jsonPath("$.email", is(receiver.getEmail())))

                .andExpect(jsonPath("$.address", is(receiver.getAddress())))
        
		        .andExpect(jsonPath("$.parcelsReceived[0].dimensions", is(parcel1.getDimensions())));

             

        // @formatter:on

		verify(mockReceiverService, times(1)).createReceiver(ArgumentMatchers.any(Receiver.class));
		// act
		// assert
	}

	@Test
	@DisplayName("Save Receiver - Negative")
	void arrangeReceiverObject_actSaveReceiver_assertReturnError() throws JsonProcessingException, Exception {
		// arrange
		// Arrange
		receiver.setName(null);
		given(mockReceiverService.createReceiver(ArgumentMatchers.any(Receiver.class)))

				.willAnswer(invocation -> invocation.getArgument(0));

		// Act - Assert

		// @formatter:off

        mockMvc.perform(post("/api/v1/receivers")

                .contentType(MediaType.APPLICATION_JSON)

                .content(objectMapper.writeValueAsString(receiver)))

                .andDo(print())

                .andExpect(status().isBadRequest());

               
             

        // @formatter:on

		verify(mockReceiverService, times(0)).createReceiver(ArgumentMatchers.any(Receiver.class));
		// act
		// assert
	}

	@Test
	@DisplayName("findReceiverByID--positive")
	void givenReceiverId_whenFindReceiverById_thenReturnReceiverObjectFromDB() throws Exception {
		// arrange
		given(mockReceiverService.getReceiverById(1L)).willReturn(receiver);
		// act-assert

		// @Formatter: off
		mockMvc.perform(get("/api/v1/receivers/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(receiver.getName())))

				.andExpect(jsonPath("$.email", is(receiver.getEmail())))

				.andExpect(jsonPath("$.address", is(receiver.getAddress())))
				.andExpect(jsonPath("$.parcelsReceived[0].dimensions", is(parcel1.getDimensions())));

		// @Formatter: on

		verify(mockReceiverService, times(1)).getReceiverById(1L);

	}

	@Test
	@DisplayName("findReceiverByID--negative")
	void givenReceiverId_whenFindReceiverById_thenReturnError() throws Exception {
		// arrange
		given(mockReceiverService.getReceiverById(2L)).willReturn(null);
		// act-assert

		// @Formatter: off
		mockMvc.perform(get("/api/v1/receivers/2")).andDo(print()).andExpect(status().isNotFound());

		// @Formatter: on

		verify(mockReceiverService, times(1)).getReceiverById(2L);

	}

	@Test
	@DisplayName("findReceiverByEmail--positive")
	void givenReceiverEmail_whenFindReceiverByEmail_thenReturnReceiverObjectFromDB() throws Exception {
		List<Receiver> receivers = new ArrayList<>();
		receivers.add(receiver);
		String email = "john.doe@mail.com";
		// arrange
		given(mockReceiverService.getReceiverByEmail(email)).willReturn(receivers);
		// act-assert

		// @Formatter: off
		mockMvc.perform(get("/api/v1/receivers/email/" + email)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))

				.andExpect(jsonPath("$[0].name", is(receiver.getName())))

				.andExpect(jsonPath("$[0].email", is(receiver.getEmail())))

				.andExpect(jsonPath("$[0].address", is(receiver.getAddress())))

				.andExpect(jsonPath("$[0].parcelsReceived[0].dimensions", is(parcel1.getDimensions())));

		// @Formatter: on

		verify(mockReceiverService, times(1)).getReceiverByEmail(email);

	}

	@Test
	@DisplayName("findReceiverByEmail--negative")
	void givenReceiverEmail_whenFindReceiverByEmail_thenReturnError() throws Exception {
		List<Receiver> emptyReceivers = new ArrayList<>();

		String email = "fake@mail.com";
		// arrange
		given(mockReceiverService.getReceiverByEmail(email)).willReturn(emptyReceivers);
		// act-assert

		// @Formatter: off
		mockMvc.perform(get("/api/v1/receivers/email/" + email))

				.andDo(print()).andExpect(status().isNotFound());

		// @Formatter: on

		verify(mockReceiverService, times(1)).getReceiverByEmail(email);

	}

	@Test
	@DisplayName("findAllReceivers")
	public void givenNothing_whenFindAllReceivers_thenReturnAllReceivers() throws Exception {
		// act
		List<Receiver> receivers = List.of(receiver);
		when(mockReceiverService.getAllReceivers()).thenReturn(receivers);
		// act-assert
		mockMvc.perform(get("/api/v1/receivers")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))

				.andExpect(jsonPath("$[0].name", is(receiver.getName())))

				.andExpect(jsonPath("$[0].email", is(receiver.getEmail())))

				.andExpect(jsonPath("$[0].address", is(receiver.getAddress())))

				.andExpect(jsonPath("$[0].parcelsReceived[0].dimensions", is(parcel1.getDimensions())));

		verify(mockReceiverService, times(1)).getAllReceivers();
	}

	@AfterEach
	void tearDown() throws Exception {

		receiver = null;
		parcel1 = null;
		parcel2 = null;
	}

}
