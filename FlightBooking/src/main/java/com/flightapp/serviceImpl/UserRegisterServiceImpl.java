package com.flightapp.serviceImpl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightapp.exception.BookingFailedException;
import com.flightapp.model.Flightapp;
import com.flightapp.model.UserRegister;
import com.flightapp.repo.FlightappRepo;
import com.flightapp.repo.UserRegisterRepo;
import com.flightapp.service.UserRegisterService;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {

	@Autowired
	UserRegisterRepo userRegisterRepo;

	@Autowired
	FlightappRepo flightappRepo;

	
	
	public String bookFlightTicket(UserRegister register, Integer flightNumber) {

		Optional<Flightapp> findById = flightappRepo.findById(flightNumber);

		Flightapp findByFlightNumber = flightappRepo.findByFlightNumber(flightNumber);
		
		if (findById.isPresent()) {

			register.setFlightNumber(flightNumber);
			register.setFlightdetails(findByFlightNumber);
			String seatNumbers = register.getSeatNumbers();
				register.setSeatNumbers(seatNumbers);
				Random rnd = new Random();
				int number = rnd.nextInt(999999);
				String pnr = String.format("%06d", number);
				register.setPnr(pnr);
				userRegisterRepo.save(register);
				return " PNR " + register.getPnr();
		} else {
			throw new BookingFailedException("Please enter correct details .. !!");
		}

	}

	@Override
	public Optional<UserRegister> getBookingDetails(String pnr) {

		Optional<UserRegister> findByPnr = userRegisterRepo.findByPnr(pnr);
		if (findByPnr.isPresent()) {
			//register.setFlightdetails(findById);
			return findByPnr;
		} else {
			throw new BookingFailedException("Please enter correct PNR Number .. !!");
		}

	}

	@Override
	public List<UserRegister> getBookingDetailsBasedOnEmail(String emailId) {
		List<UserRegister> findByEmailId = userRegisterRepo.findByEmailId(emailId);
		if(findByEmailId.isEmpty()) {
			throw new BookingFailedException("Please enter Correct Email id .. !!");
		}
			return findByEmailId;
		
	}

	@Override
	public Optional<UserRegister> deleteBookingDetails(String pnr) {
		
		Optional<UserRegister> findByPnr = userRegisterRepo.findByPnr(pnr);
		Date startDate = findByPnr.get().getFlightdetails().getStartDate();
		startDate.getHours();
		if(userRegisterRepo.findByPnr(pnr).isPresent()) {
			
			return userRegisterRepo.removeByPnr(pnr);
		}else {
			throw new BookingFailedException("Please enter correct PNR Number .. !!");
		}
	}

}
