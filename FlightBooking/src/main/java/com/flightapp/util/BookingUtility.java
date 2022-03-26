package com.flightapp.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.flightapp.model.BookingRegister;
import com.flightapp.model.ResponseMessage;

public class BookingUtility {

	public static List<String> validateBookingRegister(BookingRegister bookingRegister) {

		List<String> error = new ArrayList<>();
		if (bookingRegister.getName() == null) {
			error.add("Name");
		}
		if (bookingRegister.getEmailId() == null) {
			error.add("Name");
		}
		if (bookingRegister.getNoOfSeatstoBook() == null) {
			error.add("Name");
		}
		if (bookingRegister.getGender() == null) {
			error.add("Name");
		}
		if (bookingRegister.getSeatNumbers() == null) {
			error.add("Name");
		}
		if (bookingRegister.getRoundTripStatus() == null) {
			error.add("Name");
		}
		if (bookingRegister.getMealType() == null) {
			error.add("Name");
		}
		if (bookingRegister.getAge() == null) {
			error.add("Name");
		}

		return error;

	}

	public static ResponseMessage prepareErrorMessage(List<String> errors) {

		String finalMessage = String.join(", ", errors) + " feilds are mandatory";
		return new ResponseMessage(finalMessage, LocalDateTime.now(), HttpStatus.BAD_REQUEST);

	}

	public static ResponseEntity<Object> prepareBadRequest(String message) {
		return new ResponseEntity<>(new ResponseMessage(message, LocalDateTime.now(), HttpStatus.BAD_REQUEST),
				HttpStatus.BAD_REQUEST);

	}
}
