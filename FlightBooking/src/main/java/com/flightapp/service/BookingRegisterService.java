package com.flightapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.flightapp.model.BookingRegister;
import com.flightapp.model.SelectedSeats;

public interface BookingRegisterService {

	ResponseEntity<Object> bookFlightTicket(BookingRegister register , Integer id , SelectedSeats seats);

	Optional<BookingRegister> getBookingDetails(String pnr);

	List<BookingRegister> getBookingDetailsBasedOnEmail(String emailId);

	Optional<BookingRegister> deleteBookingDetails(String pnr);

}
