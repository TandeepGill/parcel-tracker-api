package com.fdmgroup.parceltracker.service;

import java.util.List;

import com.fdmgroup.parceltracker.model.Sender;

public interface SenderService {

	List<Sender> getAllSenders();

	Sender getSenderById(Long id);

	List<Sender> getSenderByEmail(String email);

	Sender createSender(Sender sender);

}
