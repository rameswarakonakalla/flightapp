package com.flightapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.flightapp.model.BookingRegister;

public interface BookingRegisterService {

	String bookFlightTicket(BookingRegister register , Integer id);

	Optional<BookingRegister> getBookingDetails(String pnr);

	List<BookingRegister> getBookingDetailsBasedOnEmail(String emailId);

	Optional<BookingRegister> deleteBookingDetails(String pnr);

	//public ResponseEntity<Object> deleteBookingDetails(String pnr);
	
}
