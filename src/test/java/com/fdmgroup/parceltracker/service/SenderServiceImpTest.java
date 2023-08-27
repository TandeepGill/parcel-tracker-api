package com.fdmgroup.parceltracker.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fdmgroup.parceltracker.model.Parcel;
import com.fdmgroup.parceltracker.model.Sender;
import com.fdmgroup.parceltracker.repository.SenderRepository;

@ExtendWith(MockitoExtension.class)
class SenderServiceImpTest {

	@InjectMocks
	private SenderServiceImp senderServiceImp;

	@Mock
	private SenderRepository mockSenderRepository;

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
		parcel2 = new Parcel();
		List<Parcel> parcels = new ArrayList<>();
//		parcels.add(parcel1);
//		parcels.add(parcel2);
//		sender.setParcelsReceived(parcels);
	}

	@Test
	@DisplayName("Find all senders - none")
	void arrangeEmptyListSender_actGetAllSenders_assertReturnsEmptyList() {
		// arrange
		List<Sender> senders = new ArrayList<>();
		when(mockSenderRepository.findAll()).thenReturn(senders);

		// act
		List<Sender> findAllSenders = senderServiceImp.getAllSenders();

		// assert
		assertThat(findAllSenders.size()).isEqualTo(0);
		verify(mockSenderRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("Find all senders - one")
	void arrangeListSender_actGetAllSenders_assertReturnsList() {
		// arrange
		List<Parcel> parcels = new ArrayList<>();
		parcels.add(parcel1);
		parcels.add(parcel2);
		sender.setParcelsSent(parcels);
		List<Sender> senders = new ArrayList<>();
		senders.add(sender);
		when(mockSenderRepository.findAll()).thenReturn(senders);

		// act
		List<Sender> findAllSenders = senderServiceImp.getAllSenders();

		// assert
		assertThat(findAllSenders.size()).isEqualTo(1);
		assertThat(findAllSenders.get(0).getName()).isEqualTo(sender.getName());
		assertThat(findAllSenders.get(0).getId()).isEqualTo(sender.getId());
		assertThat(findAllSenders.get(0).getParcelsSent()).isEqualTo(sender.getParcelsSent());
		verify(mockSenderRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("Find Sender by Email - Positive")
	void arrangeSenderList_actGetSenderByEmail_assertCorrectEmailAddress() {
		// arrange
		List<Parcel> parcels = new ArrayList<>();
		parcels.add(parcel1);
		parcels.add(parcel2);
		sender.setParcelsSent(parcels);
		List<Sender> senders = new ArrayList<>();
		senders.add(sender);
		String email = "john.doe@mail.com";
		when(mockSenderRepository.findByEmail(email)).thenReturn(senders);

		// act
		List<Sender> findSendersByEmail = senderServiceImp.getSenderByEmail(email);

		// assert
		assertThat(findSendersByEmail.size()).isEqualTo(1);
		assertThat(findSendersByEmail.get(0).getName()).isEqualTo(sender.getName());
		assertThat(findSendersByEmail.get(0).getId()).isEqualTo(sender.getId());
		assertThat(findSendersByEmail.get(0).getEmail()).isEqualTo(email);
		assertThat(findSendersByEmail.get(0).getParcelsSent()).isEqualTo(sender.getParcelsSent());
		verify(mockSenderRepository, times(1)).findByEmail(email);
	}

	@Test
	@DisplayName("Find Sender by Email - Negative")
	void arrangeSenderList_actGetSenderByEmail_assertEmptyListOfSenders() {
		// arrange
		List<Sender> noMatchingSenders = new ArrayList<>();
		String email = "jack.doe@mail.com";
		when(mockSenderRepository.findByEmail(email)).thenReturn(noMatchingSenders);

		// act
		List<Sender> findSendersByEmail = senderServiceImp.getSenderByEmail(email);

		// assert
		assertThat(findSendersByEmail.size()).isEqualTo(0);
		verify(mockSenderRepository, times(1)).findByEmail(email);
	}

	@Test
	@DisplayName("Create Sender")
	void arrangeSenderObject_actCreateSender_assertReturnSenderObject() {
		// arrange
		List<Parcel> parcels = new ArrayList<>();
		parcels.add(parcel1);
		parcels.add(parcel2);
		sender.setParcelsSent(parcels);
		when(mockSenderRepository.save(sender)).thenReturn(sender);

		// act
		Sender created = senderServiceImp.createSender(sender);

		// assert
		assertThat(created).isNotNull();
		assertThat(created.getId()).isEqualTo(sender.getId());
		assertThat(created.getName()).isEqualTo(sender.getName());
		assertThat(created.getEmail()).isEqualTo(sender.getEmail());
		assertThat(created.getParcelsSent()).isEqualTo(sender.getParcelsSent());
		verify(mockSenderRepository, times(1)).save(sender);
	}

	@Test
	@DisplayName("Find Sender By Id - Positive")
	void arrangeOptionalSenderObject_actGetSenderById_assertReturnCorrectSender() {
		// arrange
		Optional<Sender> optionalSender = Optional.of(sender);
		Long id = 1L;
		when(mockSenderRepository.findById(id)).thenReturn(optionalSender);

		// act
		Sender actual = senderServiceImp.getSenderById(id);

		// assert
		assertThat(actual).isNotNull();
		assertThat(actual.getId()).isEqualTo(sender.getId());
		assertThat(actual.getName()).isEqualTo(sender.getName());
		assertThat(actual.getEmail()).isEqualTo(sender.getEmail());
		verify(mockSenderRepository, times(1)).findById(id);
	}

	@Test
	@DisplayName("Find Sender By Id - Negative")
	void arrange_actGetSenderById_assertReturnNull() {
		// arrange
		Optional<Sender> optionalSender = Optional.ofNullable(null);
		Long id = 2L;
		when(mockSenderRepository.findById(id)).thenReturn(optionalSender);

		// act
		Sender actual = senderServiceImp.getSenderById(id);

		// assert
		assertThat(actual).isNull();
		verify(mockSenderRepository, times(1)).findById(id);
	}

	@AfterEach
	void tearDown() throws Exception {
		sender = null;
		parcel1 = null;
		parcel2 = null;
	}

}
