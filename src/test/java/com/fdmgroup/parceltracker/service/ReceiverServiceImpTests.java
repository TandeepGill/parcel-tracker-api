package com.fdmgroup.parceltracker.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fdmgroup.parceltracker.model.Parcel;
import com.fdmgroup.parceltracker.model.Receiver;
import com.fdmgroup.parceltracker.repository.ReceiverRepository;

@ExtendWith(MockitoExtension.class)
class ReceiverServiceImpTests {
	
	@InjectMocks
	private ReceiverServiceImp receiverServiceImp;
	
	@Mock
	private ReceiverRepository mockReceiverRepository;
	
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
		parcel2= new Parcel();
		List<Parcel> parcels = new ArrayList<>();
//		parcels.add(parcel1);
//		parcels.add(parcel2);
//		receiver.setParcelsReceived(parcels);
	}
	
	@Test
	@DisplayName("Find all receivers - none")
	void arrangeEmptyListReceiver_actGetAllReceivers_assertReturnsEmptyList() {
		//arrange
		List<Receiver> receivers = new ArrayList<>();
		when(mockReceiverRepository.findAll()).thenReturn(receivers);
		
		//act
		List<Receiver> findAllReceivers = receiverServiceImp.getAllReceivers();
		
		//assert
		assertThat(findAllReceivers.size()).isEqualTo(0);
		verify(mockReceiverRepository, times(1)).findAll();
	}
	
	@Test
	@DisplayName("Find all receivers - one")
	void arrangeListReceiver_actGetAllReceivers_assertReturnsList() {
		//arrange
		List<Parcel> parcels = new ArrayList<>();
		parcels.add(parcel1);
		parcels.add(parcel2);
		receiver.setParcelsReceived(parcels);
		List<Receiver> receivers = new ArrayList<>();
		receivers.add(receiver);
		when(mockReceiverRepository.findAll()).thenReturn(receivers);
		
		//act
		List<Receiver> findAllReceivers = receiverServiceImp.getAllReceivers();
		
		//assert
		assertThat(findAllReceivers.size()).isEqualTo(1);
		assertThat(findAllReceivers.get(0).getName()).isEqualTo(receiver.getName());
		assertThat(findAllReceivers.get(0).getId()).isEqualTo(receiver.getId());
		assertThat(findAllReceivers.get(0).getParcelsReceived()).isEqualTo(receiver.getParcelsReceived());
		verify(mockReceiverRepository, times(1)).findAll();
	}
	
	@Test
	@DisplayName("Find Receiver by Email - Positive")
	void arrangeReceiverList_actGetReceiverByEmail_assertCorrectEmailAddress() {
		//arrange
		List<Parcel> parcels = new ArrayList<>();
		parcels.add(parcel1);
		parcels.add(parcel2);
		receiver.setParcelsReceived(parcels);
		List<Receiver> receivers = new ArrayList<>();
		receivers.add(receiver);
		String email = "john.doe@mail.com";
		when(mockReceiverRepository.findByEmail(email)).thenReturn(receivers);
		
		//act
		List<Receiver> findReceiversByEmail = receiverServiceImp.getReceiverByEmail(email);
		
		//assert
		assertThat(findReceiversByEmail.size()).isEqualTo(1);
		assertThat(findReceiversByEmail.get(0).getName()).isEqualTo(receiver.getName());
		assertThat(findReceiversByEmail.get(0).getId()).isEqualTo(receiver.getId());
		assertThat(findReceiversByEmail.get(0).getEmail()).isEqualTo(email);
		assertThat(findReceiversByEmail.get(0).getParcelsReceived()).isEqualTo(receiver.getParcelsReceived());
		verify(mockReceiverRepository, times(1)).findByEmail(email);
	}
	
	@Test
	@DisplayName("Find Receiver by Email - Negative")
	void arrangeReceiverList_actGetReceiverByEmail_assertEmptyListOfReceivers() {
		//arrange
		List<Receiver> noMatchingReceivers = new ArrayList<>();
		String email = "jack.doe@mail.com";
		when(mockReceiverRepository.findByEmail(email)).thenReturn(noMatchingReceivers);
		
		//act
		List<Receiver> findReceiversByEmail = receiverServiceImp.getReceiverByEmail(email);
		
		//assert
		assertThat(findReceiversByEmail.size()).isEqualTo(0);
		verify(mockReceiverRepository, times(1)).findByEmail(email);
	}
	
	@AfterEach
	void tearDown() throws Exception {
		receiver = null;
		parcel1 = null;
		parcel2 = null;
	}
	
	@Test
	@DisplayName("Create Receiver")
	void arrangeReceiverObject_actCreateReceiver_assertReturnReceiverObject() {
		//arrange
		List<Parcel> parcels = new ArrayList<>();
		parcels.add(parcel1);
		parcels.add(parcel2);
		receiver.setParcelsReceived(parcels);
		when(mockReceiverRepository.save(receiver)).thenReturn(receiver);

		//act
		Receiver created = receiverServiceImp.createReceiver(receiver);
		
		//assert
		assertThat(created).isNotNull();
		assertThat(created.getId()).isEqualTo(receiver.getId());
		assertThat(created.getName()).isEqualTo(receiver.getName());
		assertThat(created.getEmail()).isEqualTo(receiver.getEmail());
		assertThat(created.getParcelsReceived()).isEqualTo(receiver.getParcelsReceived());
		verify(mockReceiverRepository, times(1)).save(receiver);
	}
	
	@Test
	@DisplayName("Find Receiver By Id - Positive")
	void arrangeOptionalReceiverObject_actGetReceiverById_assertReturnCorrectReceiver() {
		//arrange
		Optional<Receiver> optionalReceiver = Optional.of(receiver);
		Long id = 1L;
		when(mockReceiverRepository.findById(id)).thenReturn(optionalReceiver);
		
		//act
		Receiver actual = receiverServiceImp.getReceiverById(id);
		
		//assert
		assertThat(actual).isNotNull();
		assertThat(actual.getId()).isEqualTo(receiver.getId());
		assertThat(actual.getName()).isEqualTo(receiver.getName());
		assertThat(actual.getEmail()).isEqualTo(receiver.getEmail());
		verify(mockReceiverRepository, times(1)).findById(id);
	}
	
	@Test
	@DisplayName("Find Receiver By Id - Negative")
	void arrange_actGetReceiverById_assertReturnNull() {
		//arrange
		Optional<Receiver> optionalReceiver = Optional.ofNullable(null);
		Long id = 2L;
		when(mockReceiverRepository.findById(id)).thenReturn(optionalReceiver);
		
		//act
		Receiver actual = receiverServiceImp.getReceiverById(id);
		
		//assert
		assertThat(actual).isNull();
		verify(mockReceiverRepository, times(1)).findById(id);
	}



}
