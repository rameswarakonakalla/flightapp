package com.flightapp.service;

import java.util.List;
import java.util.Optional;

import com.flightapp.model.UserRegister;

public interface UserRegisterService {

	String bookFlightTicket(UserRegister register , Integer id);

	Optional<UserRegister> getBookingDetails(String pnr);

	List<UserRegister> getBookingDetailsBasedOnEmail(String emailId);

	Optional<UserRegister> deleteBookingDetails(String pnr);

	
}
