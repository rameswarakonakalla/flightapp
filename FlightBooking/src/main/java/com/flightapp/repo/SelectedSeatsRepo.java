package com.flightapp.repo;

import java.time.LocalDate;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flightapp.model.SelectedSeats;

@Repository
public interface SelectedSeatsRepo extends JpaRepository<SelectedSeats, Integer> {

	public Optional<SelectedSeats> findByStartDateAndSeatNumbersAndFlightNumber(LocalDate startDate , String seatNumbers , Integer flightNumber);
	
	@Transactional
	Optional<SelectedSeats> removeByPnr(String pnr);
//	
//	
//	INSERT INTO `flightappschema`.`selected_seats` (`email`, `flight_number`, `pnr`, `seat_numbers`, `start_date`) VALUES ('k@g', '1', '32', 'dd', 'eee');
//	
//	@Query(value ="insert into selectedSeats(email,flightNumber,pnr,seatNumbers,startDate"  values = "?,?,?,?,?");
//	void saveAs(SelectedSeats seats);
}
