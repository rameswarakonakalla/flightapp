package com.flightapp.model;

import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class UserRegister {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id ;
	
	
	private String name; 
	
	private String emailId;
	
	private Integer noOfSeatstoBook;
	
	private String gender;
	
	private Integer age;
	
	private String pnr;
	
	private String seatNumbers ;
	
	private Integer FlightNumber;
	
	@ManyToOne
	private Flightapp flightdetails;
}
