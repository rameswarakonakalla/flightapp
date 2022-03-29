package com.flightapp.repo;

import java.time.LocalDate;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightapp.model.SelectedSeats;

@Repository
public interface SelectedSeatsRepo extends JpaRepository<SelectedSeats, Integer> {

	public Optional<SelectedSeats> findByStartDateAndSeatNumbersAndFlightNumber(LocalDate startDate , String seatNumbers , Integer flightNumber);
	
	@Transactional
	Optional<SelectedSeats> removeByPnr(String pnr);
}
