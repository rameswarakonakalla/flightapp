package com.flightapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.flightapp.feignclients.AuthFeign;
import com.flightapp.model.AuthResponse;
import com.flightapp.model.Flightapp;
import com.flightapp.model.FlightappUpdate;
import com.flightapp.service.FlightappService;

@ExtendWith(MockitoExtension.class)
class FlightappAdminControllerTest {

	/*
	 * @InjectMocks StudentController studentController ; //dependent
	 * 
	 * @Mock StudentService service;
	 */

	@InjectMocks
	FlightappAdminController flightappAdminController;

	@Mock
	FlightappService flightappService;

	@Mock
	AuthFeign authFeign;

	@Test
	void testsaveFlightInfo() {
		Flightapp flight = new Flightapp();
		flight.setFlightNumber(1);
		flight.setAirline("Air-asia");
		flight.setToplace("Hyd");
		flight.setFromplace("Vij");
		flight.setFlightStatus(true);
		AuthResponse tokenValid = new AuthResponse("admin", true, "token");
		ResponseEntity<AuthResponse> response = new ResponseEntity<AuthResponse>(tokenValid, HttpStatus.OK);

		when(authFeign.getAdminValidity("token")).thenReturn(response);
		when(flightappService.saveFlightInfo(flight))
				.thenReturn((new ResponseEntity<Object>(flight.getFlightNumber(), HttpStatus.OK)));
		ResponseEntity<Object> saveFlightInfo = flightappAdminController.saveFlightInfo("token", flight);
		assertEquals(new ResponseEntity<>(flight.getFlightNumber(), HttpStatus.OK), saveFlightInfo);

	}

	@Test
	void testSearchFlight() {
		Flightapp flight = new Flightapp();
		flight.setFlightNumber(1);
		flight.setAirline("Air-asia");
		flight.setToplace("Hyd");
		flight.setFromplace("Vij");
		flight.setFlightStatus(true);
		flight.setStartDate(LocalDate.now());
//		AuthResponse tokenValid = new AuthResponse("admin", true ,"token");
//		ResponseEntity<AuthResponse> response = new ResponseEntity<AuthResponse>(tokenValid, HttpStatus.OK);
		// when(authFeign.getAdminValidity("token")).thenReturn(response);
		when(flightappService.searchFlight(flight)).thenReturn((new ResponseEntity<Object>(flight, HttpStatus.OK)));
		ResponseEntity<Object> searchFlight = flightappAdminController.searchFlight(flight);
		assertEquals(new ResponseEntity<>(flight, HttpStatus.OK), searchFlight);
	}

	@Test
	void testSaveInventory() {
		FlightappUpdate flight = new FlightappUpdate();

		flight.setFlightNumber(1);
		flight.setAirline("Air-asia");
		flight.setToplace("Hyd");
		flight.setFromplace("Vij");
		flight.setFlightStatus(true);

		AuthResponse tokenValid = new AuthResponse("admin", true, "token");
		ResponseEntity<AuthResponse> response = new ResponseEntity<AuthResponse>(tokenValid, HttpStatus.OK);
		when(authFeign.getAdminValidity("token")).thenReturn(response);
		when(flightappService.saveInventory(flight)).thenReturn((new ResponseEntity<Object>(flight, HttpStatus.OK)));
		ResponseEntity<Object> saveInventory = flightappAdminController.saveInventory("token", flight);
		assertEquals(new ResponseEntity<>(flight, HttpStatus.OK), saveInventory);
	}
}
