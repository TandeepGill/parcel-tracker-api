package com.fdmgroup.parceltracker.service;

import java.util.List;

import com.fdmgroup.parceltracker.model.Receiver;

public interface ReceiverService {

	List<Receiver> getAllReceivers();

	List<Receiver> getReceiverByEmail(String email);

	Receiver createReceiver(Receiver receiver);

	Receiver getReceiverById(Long id);

}
