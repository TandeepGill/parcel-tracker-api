package com.fdmgroup.parceltracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fdmgroup.parceltracker.model.Parcel;

public interface ParcelRepository extends JpaRepository<Parcel, Long> {
	@Query("SELECT p FROM Parcel p WHERE p.trackingNumber = :trackingNumber")
	Parcel findByTrackingNumber(@Param("trackingNumber") String trackingNumber);
}
