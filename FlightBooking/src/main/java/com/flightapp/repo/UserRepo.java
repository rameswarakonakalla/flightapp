package com.flightapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightapp.model.UserData;

@Repository
public interface UserRepo extends JpaRepository<UserData, String> {

}