package com.fdmgroup.parceltracker.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.parceltracker.model.Parcel;
import com.fdmgroup.parceltracker.model.Receiver;
import com.fdmgroup.parceltracker.model.Sender;
import com.fdmgroup.parceltracker.service.ParcelService; // Use the interface instead of the implementation

@WebMvcTest(ParcelController.class)
public class ParcelControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ParcelService parcelService; // Use the interface instead of the implementation

	private Parcel parcel;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setUp() {
		parcel = new Parcel();
		parcel.setId(1L);
		parcel.setWeight(1.2);
		parcel.setDimensions("30x30x50");
		parcel.setTrackingNumber("f99cf3067f0e44aba5927351a789ab7d");
		parcel.setDeliveryStatus("Preparing");
		parcel.setSender(new Sender());
		parcel.setReceiver(new Receiver());
		
		parcel.getSender().setName("Joe");
		parcel.getReceiver().setName("Mike");
	}

	@Test
	@DisplayName("findParcelByTrackingNumber-positive")
	public void givenParcelTrackingNumber_whenFindParcelByTrackingNumber_thenReturnParcelObjectFromDB()
			throws Exception {
		given(parcelService.findParcelByTrackingNumber("f99cf3067f0e44aba5927351a789ab7d")).willReturn(parcel);

		//@formatter:off
        mockMvc.perform(get("/api/v1/parcel/f99cf3067f0e44aba5927351a789ab7d"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.weight", is(parcel.getWeight())))
                .andExpect(jsonPath("$.dimensions", is(parcel.getDimensions())))
                .andExpect(jsonPath("$.sender.name", is("Joe")))
                .andExpect(jsonPath("$.receiver.name", is("Mike")));
        //@formatter:on

		verify(parcelService, times(1)).findParcelByTrackingNumber("f99cf3067f0e44aba5927351a789ab7d");
	}

	@Test
	@DisplayName("findParcelByTrackingNumber-negative")
	public void givenParcelTrackingNumber_whenFindParcelByTrackingNumber_thenReturnNotFound() throws Exception {
		String trackingNumber = "f99cf3067f0e44aba5927351a789ab7d";
		given(parcelService.findParcelByTrackingNumber(trackingNumber)).willReturn(null);
		//@formatter:off
		mockMvc.perform(get("/api/v1/parcel/f99cf3067f0e44aba5927351a789ab7d"))
		.andDo(print())
		.andExpect(status().isNotFound());
		//@formatter:on
		verify(parcelService, times(1)).findParcelByTrackingNumber(trackingNumber);
	}

	@Test
	@DisplayName("FindAllParcels")
	public void givenNothing_whenFindAllParcel_thenReturnAllSavedParcel() throws Exception {
		List<Parcel> parcels = List.of(parcel);
		when(parcelService.findAllParcels()).thenReturn(parcels);

		mockMvc.perform(get("/api/v1/parcel")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].weight", is(parcel.getWeight())));

		verify(parcelService, times(1)).findAllParcels();

	}

	@Test
	@DisplayName("Delete parcel - positive")
	public void givenParcelId_whenDeleteParcelById_thenReturnGone() throws Exception {
		// given
		Long id = 1L;
		given(parcelService.deleteParcelById(id)).willReturn(true);

		// when-then
		mockMvc.perform(delete("/api/v1/parcel/1")).andDo(print()).andExpect(status().isOk());

		verify(parcelService, times(1)).deleteParcelById(id);
	}

	@Test
	@DisplayName("Delete parcel - negative")
	public void givenNonExistentParcelId_whenDeleteParcelById_thenReturnNotFound() throws Exception {
		// given
		Long id = 2L;
		given(parcelService.deleteParcelById(id)).willReturn(false);

		// when-then
		mockMvc.perform(delete("/api/v1/parcel/2")).andDo(print()).andExpect(status().isNotFound());

		verify(parcelService, times(1)).deleteParcelById(id);
	}

	@Test
	public void testUpdateParcelById() throws Exception {
	    when(parcelService.updateParcelById(eq(1L), any(Parcel.class))).thenReturn(true);

	    mockMvc.perform(put("/api/v1/parcel/{id}", 1L)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(parcel)))
	            .andExpect(status().isOk());
	}

	@Test
	public void testUpdateParcelByIdNotFound() throws Exception {
	    when(parcelService.updateParcelById(1L, parcel)).thenReturn(false);

	    mockMvc.perform(put("/api/v1/parcel/{id}", 1L)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(parcel)))
	            .andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("saveParcel-Positive")
	public void givenParcelObject_whenSaveParcel_thenReturnSavedParcel() throws Exception {
		// given
		given(parcelService.saveParcel(ArgumentMatchers.any(Parcel.class))).willReturn(parcel);

		// when & then
		
		// @formatter:off

		mockMvc.perform(post("/api/v1/parcel")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(parcel)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.weight", is(parcel.getWeight())))
				.andExpect(jsonPath("$.dimensions", is(parcel.getDimensions())));
		
		// @formatter:on
		
		verify(parcelService, times(1)).saveParcel(ArgumentMatchers.any(Parcel.class));
	}
	
	@Test
	@DisplayName("saveParcel-Negative")
	public void givenParcelObject_whenSaveParcel_thenReturnError() throws Exception {
		// given
		parcel.setDimensions(null);
		given(parcelService.saveParcel(ArgumentMatchers.any(Parcel.class))).willReturn(parcel);

		// when & then
		
		// @formatter:off

		mockMvc.perform(post("/api/v1/parcel")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(parcel)))
				.andDo(print())
				.andExpect(status().isBadRequest());
		
		// @formatter:on
		
		verify(parcelService, times(0)).saveParcel(ArgumentMatchers.any(Parcel.class));
	}

	@AfterEach
	public void tearDown() {
		parcel = null;
	}
}