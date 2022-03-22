package com.flightapp.service;

import java.text.SimpleDateFormat;
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

@Service
public class UserRegisterServiceImpl implements UserRegisterService {

	@Autowired
	UserRegisterRepo userRegisterRepo;

	@Autowired
	FlightappRepo flightappRepo;

	public final static long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;
	
	public String bookFlightTicket(UserRegister register, Integer flightNumber) {

		Optional<Flightapp> findById = flightappRepo.findById(flightNumber);
//		System.out.println("------------------------" + findById);

		if (findById.isPresent()) {

			register.setFlightNumber(flightNumber);
			
			String seatNumbers = register.getSeatNumbers();
			System.out.println("**************" +seatNumbers.length());
//			if(seatNumbers.length() == register.getNoOfSeatstoBook()) {
				register.setSeatNumbers(seatNumbers);
				Random rnd = new Random();
				int number = rnd.nextInt(999999);
				String pnr = String.format("%06d", number);
				register.setPnr(pnr);
				userRegisterRepo.save(register);
				return " PNR " + register.getPnr();
//			}else {
//				throw new BookingFailedException("Please enter correct seat  details .. !!");
//			}
			
			
		} else {
			throw new BookingFailedException("Please enter correct details .. !!");
		}

	}

	@Override
	public Optional<UserRegister> getBookingDetails(String pnr) {

		Optional<UserRegister> findByPnr = userRegisterRepo.findByPnr(pnr);

		if (findByPnr.isPresent()) {
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
		
		 
		if(userRegisterRepo.findByPnr(pnr).isPresent()) {
			
			return userRegisterRepo.removeByPnr(pnr);
		}else {
			throw new BookingFailedException("Please enter correct PNR Number .. !!");
		}
	}

}
