package com.flightapp.repo;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightapp.model.UserRegister;

@Repository
public interface UserRegisterRepo extends JpaRepository<UserRegister,Integer> {

	Optional<UserRegister> findByPnr(String pnr);
	
	List<UserRegister> findByEmailId(String emailId);
	
	@Transactional
	Optional<UserRegister> removeByPnr(String pnr);
}
