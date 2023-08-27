package com.fdmgroup.parceltracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.parceltracker.model.Sender;

public interface SenderRepository extends JpaRepository<Sender, Long> {

	List<Sender> findByEmail(String email);
}
