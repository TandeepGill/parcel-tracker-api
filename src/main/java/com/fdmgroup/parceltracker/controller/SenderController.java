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

import com.fdmgroup.parceltracker.model.Sender;
import com.fdmgroup.parceltracker.service.SenderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/senders")
@CrossOrigin(origins="http://localhost:3000")
public class SenderController {

	private SenderService senderService;

	public SenderController(SenderService senderService) {
		super();
		this.senderService = senderService;
	}

	@Operation(summary = "Creates a new Sender resource with the Sender that is given.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Sender resource successfully created.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "400", description = "Sender resource has invalid field(s).") })
	@PostMapping
	public ResponseEntity<?> saveSender(@Valid @RequestBody Sender sender, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();

			for (FieldError error : bindingResult.getFieldErrors()) {

				errors.put(error.getField(), error.getDefaultMessage());
			}

			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<>(this.senderService.createSender(sender), HttpStatus.CREATED);

	}
	@Operation(summary = "Retrieves a Sender resource from the database with the id that is given.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sender resource successfully retrieved.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No Sender found for that id.") })
	@GetMapping("/{id}")
	public ResponseEntity<?> findSenderById(@PathVariable Long id) {
		Sender result = this.senderService.getSenderById(id);

		if (result != null) {

			return new ResponseEntity<>(result, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@Operation(summary = "Retrieves a list of Senders from the database with the email that is given.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sender(s) successfully retrieved.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No Sender found for that email.") })
	@GetMapping("/email/{email}")
	public ResponseEntity<?> findSenderByEmail(@PathVariable String email) {
		List<Sender> result = this.senderService.getSenderByEmail(email);

		if (result.size() > 0) {

			return new ResponseEntity<>(result, HttpStatus.OK);

		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@Operation(summary = "Retrieves a list of all Senders from the database.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Senders successfully retrieved.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) })})
	@GetMapping
	public ResponseEntity<?> findAllSenders() {
		return new ResponseEntity<>(this.senderService.getAllSenders(), HttpStatus.OK);

	}

}
