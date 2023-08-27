package com.fdmgroup.parceltracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.parceltracker.model.Parcel;
import com.fdmgroup.parceltracker.repository.ParcelRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class ParcelServiceImp implements ParcelService {
	@Autowired
	private ParcelRepository parcelRepository;

	public ParcelServiceImp(ParcelRepository parcelRepository) {
		this.parcelRepository = parcelRepository;
	}

	@Override
	public Parcel saveParcel(Parcel parcel) {
		return this.parcelRepository.save(parcel);
	}

	@Override
	public List<Parcel> findAllParcels() {
		return this.parcelRepository.findAll();
	}

	@Override
	public Parcel findParcelByTrackingNumber(String trackingNumber) {
		return this.parcelRepository.findByTrackingNumber(trackingNumber);
	}

	@Override
	public boolean updateParcelById(Long id, Parcel parcel) {
		if (this.parcelRepository.existsById(parcel.getId())) {
			this.parcelRepository.save(parcel);
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteParcelById(Long id) {
		if (this.parcelRepository.existsById(id)) {
			this.parcelRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
