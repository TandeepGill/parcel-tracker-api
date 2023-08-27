package com.fdmgroup.parceltracker.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Receiver extends Customer {
	@OneToMany(mappedBy = "receiver")
	private List<Parcel> parcelsReceived;

	public Receiver() {
		super();
		this.parcelsReceived = new ArrayList<>();
	}

	public Receiver(String name, String email, String address, List<Parcel> parcelsReceived) {
		super(name, email, address);
		this.parcelsReceived = parcelsReceived;
	}

	public List<Parcel> getParcelsReceived() {
		return parcelsReceived;
	}

	public void setParcelsReceived(List<Parcel> parcelsReceived) {
		this.parcelsReceived = parcelsReceived;
	}

}
