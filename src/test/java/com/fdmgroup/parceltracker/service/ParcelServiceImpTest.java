package com.fdmgroup.parceltracker.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
import com.fdmgroup.parceltracker.model.Sender;
import com.fdmgroup.parceltracker.repository.ParcelRepository;

@ExtendWith(MockitoExtension.class)
class ParcelServiceImpTest {
	
	@InjectMocks
	private ParcelServiceImp parcelService;
	
	@Mock
	private ParcelRepository mockParcelRepository;
	
	private Parcel parcel;
	
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
	}

	@Test
	@DisplayName("saveParcel")
	void arrangeParcelObject_actSaveParcel_assertCheckParcelSaveInDB() {
		// arrange
		when(mockParcelRepository.save(parcel)).thenReturn(parcel);
		
		// act
		parcelService.saveParcel(parcel);
		
		// assert
		verify(mockParcelRepository, times(1)).save(parcel);
	}
	
	@Test
	@DisplayName("findAllParcels")
	void arrangeParcelList_actFindAllParcels_assertReturnParcelList() {
		// arrange
		List<Parcel> parcels = new ArrayList<>();
		parcels.add(parcel);
		when(mockParcelRepository.findAll()).thenReturn(parcels);
		
		// act
		List<Parcel> actual = parcelService.findAllParcels();
		
		// assert
		assertThat(actual).isNotNull();
		assertThat(actual.size()).isEqualTo(1);
		assertThat(actual.get(0).getDimensions()).isEqualTo(parcel.getDimensions());
		verify(mockParcelRepository, times(1)).findAll();
	}
	
	@Test
	@DisplayName("findParcelByTrackingNumber")
	void arrangeTrackingNumber_actFindParcelByTrackingNumber_assertReturnParcelWithMatchingTrackingNumber() {
		// arrange
		String trackingNumber = "f99cf3067f0e44aba5927351a789ab7d";
		when(mockParcelRepository.findByTrackingNumber(trackingNumber)).thenReturn(parcel);
		
		// act
		Parcel actualParcel = parcelService.findParcelByTrackingNumber(trackingNumber);
		
		// assert
		assertThat(actualParcel).isEqualTo(parcel);
		assertThat(actualParcel.getTrackingNumber()).isEqualTo(parcel.getTrackingNumber());
		verify(mockParcelRepository, times(1)).findByTrackingNumber(trackingNumber);
	}
	
	
	@Test
	@DisplayName("deleteParcelByIdTrue")
	void arrangeParcelId_actDeleteParcelById_assertReturnsTrueIfDeleted() {
		// arrange
		Long id = 1L;
		when(mockParcelRepository.existsById(id)).thenReturn(true);
		
		// act
		boolean parcelDeletedConfirmation = parcelService.deleteParcelById(id);
		
		// assert
		assertThat(parcelDeletedConfirmation).isEqualTo(true);
		verify(mockParcelRepository, times(1)).deleteById(id);
	}
	
	@Test
	@DisplayName("deleteParcelByIdFalse")
	void arrangeParcelId_actDeleteParcelById_assertReturnsFalseIfNotDeleted() {
		// arrange
		Long id = 10L;
		when(mockParcelRepository.existsById(id)).thenReturn(false);
		
		// act
		boolean parcelDeletedConfirmation = parcelService.deleteParcelById(id);
		
		// assert
		assertThat(parcelDeletedConfirmation).isEqualTo(false);
	}
	
	@Test
	@DisplayName("updateParcelByIdTrue")
	void arrangeParcelId_actUpdateParcelById_assertReturnsTrueIfUpdated() {
		// arrange
		Long id = 1L;
		when(mockParcelRepository.existsById(id)).thenReturn(true);
		
		// act
		boolean parcelUpdatedConfirmation = parcelService.updateParcelById(id, parcel);
		
		// assert
		assertThat(parcelUpdatedConfirmation).isEqualTo(true);
		verify(mockParcelRepository, times(1)).save(parcel);
	}
	
	@Test
	@DisplayName("updateParcelByIdFalse")
	void arrangeParcelId_actUpdateParcelById_assertReturnsFalseIfNotUpdated() {
		// arrange
		Long id = 10L;
		parcel.setId(id);
		when(mockParcelRepository.existsById(id)).thenReturn(false);
		
		// act
		boolean parcelUpdatedConfirmation = parcelService.updateParcelById(id, parcel);
		
		// assert
		assertThat(parcelUpdatedConfirmation).isEqualTo(false);
	}
	
	@AfterEach
	public void tearDown() {
		parcel = null;
	}

}
