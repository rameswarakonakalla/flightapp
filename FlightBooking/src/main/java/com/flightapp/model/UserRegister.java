package com.flightapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class UserRegister {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;
	
	
	private String name; 
	
	private String emailId;
	
	private int noOfSeatstoBook;
	
	private String gender;
	
	private int age;
	
	private String pnr;
	
	private String seatNumbers ;
	
	private int FlightNumber;
	
}
