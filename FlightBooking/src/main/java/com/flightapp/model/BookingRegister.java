package com.flightapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class BookingRegister {

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
	
	private Integer flightNumber;
	
	private Double totalBasePrice;
	
	@ManyToOne
	private Flightapp flightdetails;
}
