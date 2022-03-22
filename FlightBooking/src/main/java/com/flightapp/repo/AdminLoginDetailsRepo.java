package com.flightapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightapp.model.AdminLoginDetails;

@Repository
public interface AdminLoginDetailsRepo extends JpaRepository<AdminLoginDetails, Integer>{

}
