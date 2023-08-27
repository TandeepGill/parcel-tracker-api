package com.fdmgroup.parceltracker.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Sender extends Customer {
	@OneToMany(mappedBy = "sender")
	private List<Parcel> parcelsSent;

	public Sender() {
		super();
		this.parcelsSent = new ArrayList<>();
	}

	public Sender(String name, String email, String address, List<Parcel> parcelsSent) {
		super(name, email, address);
		this.parcelsSent = parcelsSent;
	}

	public List<Parcel> getParcelsSent() {
		return parcelsSent;
	}

	public void setParcelsSent(List<Parcel> parcelsSent) {
		this.parcelsSent = parcelsSent;
	}
	
	

}
