package com.flightapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightapp.model.UserData;


@Repository
public interface UserRepository extends JpaRepository<UserData, String> {

}