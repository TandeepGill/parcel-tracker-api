package com.fdmgroup.parceltracker.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.parceltracker.model.Parcel;
import com.fdmgroup.parceltracker.service.ParcelService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/parcel")
@CrossOrigin(origins="http://localhost:3000")
public class ParcelController {

	@Autowired
	private ParcelService parcelService;

	@Operation(summary = "Creates a new Parcel resource with the Parcel that is given.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Parcel resource successfully created.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@PostMapping
	public ResponseEntity<?> saveParcel(@Valid @RequestBody Parcel parcel, BindingResult bindingResult) {
	
		if (bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();

			for (FieldError error : bindingResult.getFieldErrors()) {

				errors.put(error.getField(), error.getDefaultMessage());
			}

			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

		}
		
		return new ResponseEntity<>(this.parcelService.saveParcel(parcel), HttpStatus.CREATED);
				
	}

	@Operation(summary = "Update Parcel resource with the given Parcel details in the database.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Parcel resource successfully updated and returned.", headers = {
					@Header(name = "location", description = "URI to access and return sources") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@PutMapping("/{id}")
	public ResponseEntity<?> updateParcelById(@PathVariable Long id, @RequestBody Parcel parcel) {

		if (parcelService.updateParcelById(id, parcel)) {
			return ResponseEntity.ok(parcel);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@Operation(summary = "Find Parcel resource with the given trackingNumber in the database.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Parcel resource successfully returned.", headers = {
					@Header(name = "location", description = "URI to access and return sources") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@GetMapping("/{trackingNumber}")
	public ResponseEntity<Parcel> findParcelByTrackingNumber(@PathVariable String trackingNumber) {
		Parcel parcel = parcelService.findParcelByTrackingNumber(trackingNumber);

		if (parcel != null) {
			return ResponseEntity.status(HttpStatus.OK).body(parcel);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

	}
	
	@Operation(summary = "Find all Parcel resources in the database.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Parcel resource(s) successfully returned.", headers = {
					@Header(name = "location", description = "URI to access and return sources") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@GetMapping
	public ResponseEntity<?> findAllParcels() {
		return new ResponseEntity<>(this.parcelService.findAllParcels(), HttpStatus.OK);
	}

	@Operation(summary = "Delete Parcel resource with the given Id from the database.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Parcel resource successfully deleted.", headers = {
					@Header(name = "location", description = "URI to access and return sources") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteParcel(@PathVariable Long id) {
		if (parcelService.deleteParcelById(id)) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

	}

}
