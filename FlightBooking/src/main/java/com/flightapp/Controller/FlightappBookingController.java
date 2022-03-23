package com.flightapp.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.exception.InvalidTokenException;
import com.flightapp.feignClients.AuthFeign;
import com.flightapp.model.BookingRegister;
import com.flightapp.service.BookingRegisterService;

@RestController
@RequestMapping("api/v1.0/flight")

public class FlightappBookingController {

	@Autowired
	BookingRegisterService service;

	@Autowired
	AuthFeign authFeign ; 
	
	@PostMapping("/booking/{flightNumber}")
	public String bookFlightTicket(@RequestHeader("Authorization") String token , @RequestBody BookingRegister register, @PathVariable Integer flightNumber) {

		if(authFeign.getValidity(token).getBody().isValid()) {
			return service.bookFlightTicket(register, flightNumber);
		}
		throw new InvalidTokenException("Token Expired or Invalid , Login again ...");
		

	}

	@GetMapping("/ticket/{pnr}")
	public Optional<BookingRegister> getBookingDetails(@RequestHeader("Authorization") String token ,@PathVariable String pnr) {

		

		if(authFeign.getValidity(token).getBody().isValid()) {
			return service.getBookingDetails(pnr);
		}
		throw new InvalidTokenException("Token Expired or Invalid , Login again ...");
	}

	@GetMapping("booking/history/{emailId}")
	public List<BookingRegister> getBookingDetailsBasedOnEmail(@RequestHeader("Authorization") String token ,@PathVariable String emailId) {
			
		
		if(authFeign.getValidity(token).getBody().isValid()) {
			return service.getBookingDetailsBasedOnEmail(emailId);
		}
		throw new InvalidTokenException("Token Expired or Invalid , Login again ...");
	}

	@DeleteMapping("booking/cancel/{pnr}")
	public String deleteBookingDetails(@RequestHeader("Authorization") String token ,@PathVariable String pnr) {

		if(authFeign.getValidity(token).getBody().isValid()) {
			service.deleteBookingDetails(pnr);
			
			return pnr + " Details Deleted SucessFull ";
		}
		throw new InvalidTokenException("Token Expired or Invalid , Login again ...");
		

	}
	
	
	
	
}
