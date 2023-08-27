package com.fdmgroup.parceltracker.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.parceltracker.model.Receiver;
import com.fdmgroup.parceltracker.service.ReceiverService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/receivers")
@CrossOrigin(origins="http://localhost:3000")
public class ReceiverController {

	private ReceiverService receiverService;

	public ReceiverController(ReceiverService receiverService) {

		this.receiverService = receiverService;
	}

	@Operation(summary = "Creates a new Receiver resource with the Receiver that is given.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Receiver resource successfully created.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "400", description = "Receiver resource has invalid field(s).") })
	@PostMapping
	public ResponseEntity<?> saveReceiver(@Valid @RequestBody Receiver receiver, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();

			for (FieldError error : bindingResult.getFieldErrors()) {

				errors.put(error.getField(), error.getDefaultMessage());
			}

			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<>(this.receiverService.createReceiver(receiver), HttpStatus.CREATED);

	}

	@Operation(summary = "Retrieves a Receiver resource from the database with the id that is given.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Receiver resource successfully retrieved.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No Receiver found for that id.") })
	@GetMapping("/{id}")
	public ResponseEntity<?> findReceiverById(@PathVariable Long id) {
		Receiver result = this.receiverService.getReceiverById(id);

		if (result != null) {

			return new ResponseEntity<>(result, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@Operation(summary = "Retrieves a list of Receivers from the database with the email that is given.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Receiver(s) successfully retrieved.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No Receiver found for that email.") })
	@GetMapping("/email/{email}")
	public ResponseEntity<?> findReceiverByEmail(@PathVariable String email) {
		List<Receiver> result = this.receiverService.getReceiverByEmail(email);

		if (result.size() > 0) {

			return new ResponseEntity<>(result, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@Operation(summary = "Retrieves a list of all Receivers from the database.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Receivers successfully retrieved.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) })})
	@GetMapping
	public ResponseEntity<?> findAllReceivers() {
		return new ResponseEntity<>(this.receiverService.getAllReceivers(), HttpStatus.OK);

	}

}
