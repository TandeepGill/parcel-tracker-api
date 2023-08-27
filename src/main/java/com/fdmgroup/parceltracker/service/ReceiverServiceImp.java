package com.fdmgroup.parceltracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.parceltracker.model.Receiver;
import com.fdmgroup.parceltracker.repository.ReceiverRepository;

@Service
public class ReceiverServiceImp implements ReceiverService {
	@Autowired
	private ReceiverRepository receiverRepository;

	public ReceiverServiceImp(ReceiverRepository receiverRepository) {

		this.receiverRepository = receiverRepository;
	}

	@Override
	public List<Receiver> getAllReceivers() {

		return this.receiverRepository.findAll();
	}

	@Override
	public List<Receiver> getReceiverByEmail(String email) {

		return this.receiverRepository.findByEmail(email);
	}

	@Override
	public Receiver createReceiver(Receiver receiver) {

		return this.receiverRepository.save(receiver);
	}

	@Override
	public Receiver getReceiverById(Long id) {

		return this.receiverRepository.findById(id).orElse(null);
	}

}
