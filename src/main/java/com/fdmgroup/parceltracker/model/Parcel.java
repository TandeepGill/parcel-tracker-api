package com.fdmgroup.parceltracker.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
public class Parcel {

	@Id
	@Column(name = "parcel_id")
	@SequenceGenerator(name = "PARCEL_ID_GEN", sequenceName = "parcel_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARCEL_ID_GEN")
	private Long id;

	@Column(updatable = false, unique = true, name = "tracking_number")
	private String trackingNumber;

	@NotNull(message = "Weight is required.")
	private double weight;

	@NotBlank(message = "Dimensions is required.")
	private String dimensions;

	@Column(updatable = true, name = "delivery_status")
	private String deliveryStatus;

	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "FK_SENDER_ID")
	private Sender sender;

	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "FK_RECEIVER_ID")
	private Receiver receiver;

	@PrePersist
	private void createAt() {
		String trackingNumber = UUID.randomUUID().toString().replaceAll("-", "");
		this.setTrackingNumber(trackingNumber);
		this.setDeliveryStatus("Preparing");
	}
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getDimensions() {
		return dimensions;
	}

	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public Sender getSender() {
		return sender;
	}

	public void setSender(Sender sender) {
		this.sender = sender;
	}

	public Receiver getReceiver() {
		return receiver;
	}

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}
}
