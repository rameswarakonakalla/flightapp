package com.flightapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightapp.model.AdminLoginDetails;

@Repository
public interface AdminRepo extends JpaRepository<AdminLoginDetails, String> {

}
