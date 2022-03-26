package com.flightapp.repo;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightapp.model.BookingRegister;

@Repository
public interface BookingRegisterRepo extends JpaRepository<BookingRegister,Integer> {

	Optional<BookingRegister> findByPnr(String pnr);
	
	List<BookingRegister> findByEmailId(String emailId);
	
	@Transactional
	Optional<BookingRegister> removeByPnr(String pnr);
	
	
}
