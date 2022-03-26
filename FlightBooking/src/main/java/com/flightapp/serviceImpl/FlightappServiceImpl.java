package com.flightapp.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightapp.exception.UserDefinedException;
import com.flightapp.model.Flightapp;
import com.flightapp.repo.FlightappRepo;
import com.flightapp.service.FlightappService;
import com.flightapp.util.FlightppUtiluty;

@Service
public class FlightappServiceImpl implements FlightappService {

	@Autowired
	FlightappRepo repo;

	public ResponseEntity<Object> saveFlightInfo(Flightapp flightapp) {
		List<String> validateFlightapp = FlightppUtiluty.validateFlightapp(flightapp);
		if (!validateFlightapp.isEmpty()) {
			return FlightppUtiluty
					.prepareBadRequest(FlightppUtiluty.prepareErrorMessage(validateFlightapp).getMessage());
		} else if (flightapp.getScheduledDays().equalsIgnoreCase("Daily")
				|| flightapp.getScheduledDays().equalsIgnoreCase("WeekDays")
				|| flightapp.getScheduledDays().equalsIgnoreCase("Weekends")) {

			flightapp.setRoundTripCost(flightapp.getTicketCost() * 2);
//			flightapp.setRoundTripCost(flightapp.getTicketCost());
//			if (flightapp.getRoundTripStatus()) {
//				
//			} else {
//				
//			}
			flightapp.setFlightStatus(true);

			List<String> seatNumber = new ArrayList<String>();
			for (int i = 1; i <= flightapp.getTotalBusinessClassSeats() * 2; i++) {
				if (i % 2 == 0)
					seatNumber.add("B-" + i);
			}
			for (int i = 1; i <= flightapp.getTotalNonBusinessClassSeats() * 2; i++) {
				if (i % 2 != 0)
					seatNumber.add("NB-" + i);
			}

			flightapp.setSeatNumbers(seatNumber.stream().collect(Collectors.toList()).toString());
			if (flightapp.getMealType().equalsIgnoreCase("veg") || flightapp.getMealType().equalsIgnoreCase("Non-veg")
					|| flightapp.getMealType().equalsIgnoreCase("none")) {
				Flightapp save = repo.save(flightapp);
				Integer FlightNumber = save.getFlightNumber();
				return new ResponseEntity<>(FlightNumber, HttpStatus.OK);
			} else {
				return FlightppUtiluty.prepareBadRequest("Meal type should be veg/non-veg/non");
			}

		} else {
			return FlightppUtiluty.prepareBadRequest("Flight Scheduled on Daily / WeekDays / Weekends ");
		}

	}

	public ResponseEntity<Object> searchFlight(Flightapp flightapp) {

		List<String> validatesearchFlight = FlightppUtiluty.validatesearchFlight(flightapp);
		if (!validatesearchFlight.isEmpty()) {
			ResponseEntity<Object> prepareBadRequest = FlightppUtiluty
					.prepareBadRequest(FlightppUtiluty.prepareErrorMessage(validatesearchFlight).getMessage());
			return prepareBadRequest;

		} else {
			List<Flightapp> findByFromplaceAndToplace = repo.findByFromplaceAndToplaceAndStartDate(
					flightapp.getFromplace(), flightapp.getToplace(), flightapp.getStartDate());
			if (findByFromplaceAndToplace.isEmpty()) {
				throw new UserDefinedException("No Flights Found on this Date " + flightapp.getStartDate()
						+ "	!!!  Modify your search and Try again ...");
			}
			
			return new ResponseEntity<Object>(
					findByFromplaceAndToplace.stream().filter(p -> p.getFlightStatus()).collect(Collectors.toList()),
					HttpStatus.OK);
		}
	}

	public String saveInventory(Flightapp flightapp) {

		flightapp.setFlightStatus(false);
		repo.save(flightapp);
		return "Flight Id " + flightapp.getFlightNumber() + " Details Updated ";
	}

}