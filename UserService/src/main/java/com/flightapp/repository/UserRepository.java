package com.flightapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightapp.model.UserData;


@Repository
public interface UserRepository extends JpaRepository<UserData, String> {

	Optional<UserData> findByEmailid(String emailid);
	
}