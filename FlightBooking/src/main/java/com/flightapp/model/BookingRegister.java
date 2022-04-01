package com.flightapp.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BookingRegister {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id ;
	
	private String emailId;
	
	private Integer noOfSeatstoBook;
	
	private String pnr;
	
	private String seatNumbers ;
	
	private Integer flightNumber;
	
	private Double totalBasePrice;
	
	private Boolean roundTripStatus;
	
	private String mealType;
	
	@ManyToOne
	private Flightapp flightdetails;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Passenger> passengers;
	
	
	
}
