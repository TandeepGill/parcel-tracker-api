package com.fdmgroup.parceltracker.service;

import java.util.List;

import com.fdmgroup.parceltracker.model.Parcel;

public interface ParcelService {
	Parcel saveParcel(Parcel parcel);

	List<Parcel> findAllParcels();

	Parcel findParcelByTrackingNumber(String trackingNumber);

	boolean updateParcelById(Long id, Parcel parcel);

	boolean deleteParcelById(Long id);

}
