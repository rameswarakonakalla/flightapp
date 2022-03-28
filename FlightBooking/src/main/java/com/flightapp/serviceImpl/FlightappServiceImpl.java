package com.flightapp.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightapp.exception.UserDefinedException;
import com.flightapp.model.Flightapp;
import com.flightapp.model.FlightappUpdate;
import com.flightapp.repo.FlightappRepo;
import com.flightapp.service.FlightappService;
import com.flightapp.util.FlightppUtility;

@Service
public class FlightappServiceImpl implements FlightappService {

	@Autowired
	FlightappRepo repo;

	public ResponseEntity<Object> saveFlightInfo(Flightapp flightapp) {
		List<String> validateFlightapp = FlightppUtility.validateFlightapp(flightapp);
		if (!validateFlightapp.isEmpty()) {
			return FlightppUtility
					.prepareBadRequest(FlightppUtility.prepareErrorMessage(validateFlightapp).getMessage());
		} else if (flightapp.getScheduledDays().equalsIgnoreCase("Daily")
				|| flightapp.getScheduledDays().equalsIgnoreCase("WeekDays")
				|| flightapp.getScheduledDays().equalsIgnoreCase("Weekends")) {

			flightapp.setRoundTripCost(flightapp.getTicketCost() * 2);
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
			System.out.println(flightapp.getStartDate().isAfter(flightapp.getEndDate()));
			if (flightapp.getStartDate().isBefore(flightapp.getEndDate())) {
				if (flightapp.getMealType().equalsIgnoreCase("veg")
						|| flightapp.getMealType().equalsIgnoreCase("Non-veg")
						|| flightapp.getMealType().equalsIgnoreCase("none")) {
					Flightapp save = repo.save(flightapp);
					Integer FlightNumber = save.getFlightNumber();
					return new ResponseEntity<>(FlightNumber, HttpStatus.OK);
				} else {
					return FlightppUtility.prepareBadRequest("Meal type should be veg/non-veg/non");
				}

			} else {
				return FlightppUtility.prepareBadRequest("End Date should be Greater than start date ");
			}

		} else {
			return FlightppUtility.prepareBadRequest("Flight Scheduled on Daily / WeekDays / Weekends ");
		}

	}

	public ResponseEntity<Object> searchFlight(Flightapp flightapp) {

		List<String> validatesearchFlight = FlightppUtility.validatesearchFlight(flightapp);
		if (!validatesearchFlight.isEmpty()) {
			ResponseEntity<Object> prepareBadRequest = FlightppUtility
					.prepareBadRequest(FlightppUtility.prepareErrorMessage(validatesearchFlight).getMessage());
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

	public ResponseEntity<Object> saveInventory(FlightappUpdate flightappupdate) {

		Optional<Flightapp> findById = repo.findById(flightappupdate.getFlightNumber());
		if (findById.isPresent()) {
			Flightapp flightappDetailsUpdate = findById.get();

			if (flightappupdate.getAirline() == null) {
				flightappDetailsUpdate.setAirline(flightappDetailsUpdate.getAirline());
			} else {
				flightappDetailsUpdate.setAirline(flightappupdate.getAirline());
			}

			if (flightappupdate.getFromplace() == null) {
				flightappDetailsUpdate.setFromplace(flightappDetailsUpdate.getFromplace());
			} else {
				flightappDetailsUpdate.setFromplace(flightappupdate.getFromplace());
			}

			if (flightappupdate.getToplace() == null) {
				flightappDetailsUpdate.setToplace(flightappDetailsUpdate.getToplace());
			} else {
				flightappDetailsUpdate.setToplace(flightappupdate.getToplace());
			}

			if (flightappupdate.getFlightStatus() == null) {
				flightappDetailsUpdate.setFlightStatus(flightappDetailsUpdate.getFlightStatus());
			} else {
				flightappDetailsUpdate.setFlightStatus(flightappupdate.getFlightStatus());
			}

			repo.save(flightappDetailsUpdate);
			return new ResponseEntity<Object>("Deatils Updated ", HttpStatus.OK);
		} else {
			return FlightppUtility.prepareBadRequest("Flight ID is not active/avalible ");
		}

	}

}