package com.flightapp.serviceimpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightapp.exception.UserDefinedException;
import com.flightapp.model.BookingRegister;
import com.flightapp.model.Flightapp;
import com.flightapp.model.SelectedSeats;
import com.flightapp.model.UserData;
import com.flightapp.repo.BookingRegisterRepo;
import com.flightapp.repo.FlightappRepo;
import com.flightapp.repo.SelectedSeatsRepo;
import com.flightapp.repo.UserRepository;
import com.flightapp.service.BookingRegisterService;
import com.flightapp.util.BookingUtility;

@Service
public class BookingRegisterServiceImpl implements BookingRegisterService {

	@Autowired
	BookingRegisterRepo bookRegisterRepo;

	@Autowired
	FlightappRepo flightappRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	SelectedSeatsRepo selectedSeatsRepo;

	public ResponseEntity<Object> bookFlightTicket(BookingRegister register, Integer flightNumber,
			SelectedSeats seats) {

		List<String> validateBookingRegister = BookingUtility.validateBookingRegister(register);
		Optional<Flightapp> findById = flightappRepo.findById(flightNumber);
		if (!validateBookingRegister.isEmpty()) {
			return BookingUtility
					.prepareBadRequest(BookingUtility.prepareErrorMessage(validateBookingRegister).getMessage());

		} else if (findById.isPresent()) {
			Flightapp findByFlightNumber = flightappRepo.findByFlightNumber(flightNumber);
			Boolean flightStatus = findByFlightNumber.getFlightStatus();
			if (Boolean.TRUE.equals(flightStatus)) {
				Optional<UserData> findByEmailid = userRepo.findByEmailid(register.getEmailId());
				if (findByEmailid.isPresent()) {
					register.setFlightNumber(flightNumber);
					register.setFlightdetails(findByFlightNumber);
					String seatNumbers = register.getSeatNumbers();

					Optional<SelectedSeats> findBystartDateAndseatNumbers = selectedSeatsRepo
							.findByStartDateAndSeatNumbersAndFlightNumber(findByFlightNumber.getStartDate(),
									seatNumbers, findById.get().getFlightNumber());

					if (findBystartDateAndseatNumbers.isEmpty()) {
						if (register.getNoOfSeatstoBook() == register.getPassengers().size()) {
							Random rnd = new Random();
							int number = rnd.nextInt(999999);
							String pnr = String.format("%06d", number);
							register.setPnr(pnr);
							register.setSeatNumbers(seatNumbers);
							seats.setFlightNumber(flightNumber);
							seats.setPnr(pnr);
							seats.setStartDate(findByFlightNumber.getStartDate());
							seats.setEmail(register.getEmailId());
							seats.setSeatNumbers(seatNumbers);
							Boolean roundTripStatus = register.getRoundTripStatus();
							if (Boolean.TRUE.equals(roundTripStatus)) {
								register.setTotalBasePrice(findById.get().getRoundTripCost()
										* seatNumbers.replaceAll("\\D+", "").length());
							} else {
								register.setTotalBasePrice(
										findById.get().getTicketCost() * seatNumbers.replaceAll("\\D+", "").length());
							}
							selectedSeatsRepo.save(seats);
							bookRegisterRepo.save(register);
							return new ResponseEntity<>(" PNR " + register.getPnr(), HttpStatus.OK);

						} else {
							return BookingUtility.prepareBadRequest("No of seats is equal to list of passengers");
						}

					} else {
						return BookingUtility.prepareBadRequest(seatNumbers + " Already Booked");
					}

				} else {
					return BookingUtility.prepareBadRequest(
							register.getEmailId() + " Do you don't have an Account .???   ...please register");
				}

			} else {
				return BookingUtility.prepareBadRequest("Meal type should be veg/non-veg/non");
			}
		} else {
			return BookingUtility.prepareBadRequest("Selected Flight is not active");

		}
	}

	@Override
	public Optional<BookingRegister> getBookingDetails(String pnr) {

		Optional<BookingRegister> findByPnr = bookRegisterRepo.findByPnr(pnr);
		if (findByPnr.isPresent()) {
			return findByPnr;
		} else {
			throw new UserDefinedException("Please enter correct PNR Number .. !!");
		}
	}

	@Override
	public List<BookingRegister> getBookingDetailsBasedOnEmail(String emailId) {
		List<BookingRegister> findByEmailId = bookRegisterRepo.findByEmailId(emailId);
		if (findByEmailId.isEmpty()) {
			throw new UserDefinedException("Please enter Correct Email id .. !!");
		}
		return findByEmailId;

	}

	@Override
	public Optional<BookingRegister> deleteBookingDetails(String pnr) {
		Optional<BookingRegister> findByPnr = bookRegisterRepo.findByPnr(pnr);
		if (findByPnr.isPresent()) {
			LocalDate startDate = findByPnr.get().getFlightdetails().getStartDate();
			LocalDate lt = LocalDate.now();
			LocalDate currentBookingDate = lt.plusDays(1);
			if (startDate.equals(currentBookingDate)) {
				throw new UserDefinedException("Before 24 hrs ticket cancel is not possible ");
			} else {
				selectedSeatsRepo.removeByPnr(pnr);
				return bookRegisterRepo.removeByPnr(pnr);
			}
		} else {
			throw new UserDefinedException("Please enter correct PNR Number .. !!");
		}
	}
}