package com.flightapp.serviceImpl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightapp.exception.UserDefinedException;
import com.flightapp.model.Flightapp;
import com.flightapp.model.BookingRegister;
import com.flightapp.repo.FlightappRepo;
import com.flightapp.repo.BookingRegisterRepo;
import com.flightapp.service.BookingRegisterService;
import com.flightapp.util.FlightppUtiluty;

@Service
public class BookingRegisterServiceImpl implements BookingRegisterService {

	@Autowired
	BookingRegisterRepo userRegisterRepo;

	@Autowired
	FlightappRepo flightappRepo;

	public String bookFlightTicket(BookingRegister register, Integer flightNumber) {

		Optional<Flightapp> findById = flightappRepo.findById(flightNumber);

		Flightapp findByFlightNumber = flightappRepo.findByFlightNumber(flightNumber);

		if (findById.isPresent()) {

			register.setFlightNumber(flightNumber);
			register.setFlightdetails(findByFlightNumber);
			String seatNumbers = register.getSeatNumbers();
			register.setSeatNumbers(seatNumbers);

			if (findById.get().getRoundTrip()) {
				register.setTotalBasePrice(
						findById.get().getRoundTripCost() * seatNumbers.replaceAll("\\D+", "").length());
			} else {
				register.setTotalBasePrice(
						findById.get().getTicketCost() * seatNumbers.replaceAll("\\D+", "").length());
			}
			System.out.println(seatNumbers);
			Random rnd = new Random();
			int number = rnd.nextInt(999999);
			String pnr = String.format("%06d", number);
			register.setPnr(pnr);
			userRegisterRepo.save(register);
			return " PNR " + register.getPnr();
		} else {
			throw new UserDefinedException("Please enter correct details .. !!");
		}

	}

	@Override
	public Optional<BookingRegister> getBookingDetails(String pnr) {

		Optional<BookingRegister> findByPnr = userRegisterRepo.findByPnr(pnr);
		if (findByPnr.isPresent()) {
			// register.setFlightdetails(findById);
			return findByPnr;
		} else {
			throw new UserDefinedException("Please enter correct PNR Number .. !!");
		}
	}

	@Override
	public List<BookingRegister> getBookingDetailsBasedOnEmail(String emailId) {
		List<BookingRegister> findByEmailId = userRegisterRepo.findByEmailId(emailId);
		if (findByEmailId.isEmpty()) {
			throw new UserDefinedException("Please enter Correct Email id .. !!");
		}
		return findByEmailId;

	}

	@Override
	public Optional<BookingRegister> deleteBookingDetails(String pnr) {
		Optional<BookingRegister> findByPnr = userRegisterRepo.findByPnr(pnr);
		LocalDate startDate = userRegisterRepo.findByPnr(pnr).get().getFlightdetails().getStartDate();
		System.out.println(startDate);
		LocalDate lt = LocalDate.now();
		System.out.println(lt);
		boolean before = lt.isBefore(startDate);
		System.out.println(lt.isBefore(startDate));

		if (before) {
			if (userRegisterRepo.findByPnr(pnr).isPresent()) {

				return userRegisterRepo.removeByPnr(pnr);
			} else {
				throw new UserDefinedException("Please enter correct PNR Number .. !!");
			}
		} else {
			throw new UserDefinedException("Before 24 hrs ticket cancel is not possible ");
		}
	}

}
