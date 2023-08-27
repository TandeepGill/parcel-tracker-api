package com.fdmgroup.parceltracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.parceltracker.model.Receiver;

public interface ReceiverRepository extends JpaRepository<Receiver, Long> {

	List<Receiver> findByEmail(String email);

}
