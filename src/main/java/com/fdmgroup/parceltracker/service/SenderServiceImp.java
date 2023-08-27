package com.fdmgroup.parceltracker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fdmgroup.parceltracker.model.Sender;
import com.fdmgroup.parceltracker.repository.SenderRepository;

@Service
public class SenderServiceImp implements SenderService {

	private SenderRepository senderRepository;

	public SenderServiceImp(SenderRepository senderRepository) {

		this.senderRepository = senderRepository;
	}

	@Override
	public List<Sender> getAllSenders() {

		return this.senderRepository.findAll();
	}

	@Override
	public Sender getSenderById(Long id) {

		return this.senderRepository.findById(id).orElse(null);
	}

	@Override
	public List<Sender> getSenderByEmail(String email) {

		return this.senderRepository.findByEmail(email);
	}

	@Override
	public Sender createSender(Sender sender) {

		return this.senderRepository.save(sender);
	}

}
