package com.flightapp.model;





import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
public class Flightapp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Integer flightNumber;
	
	private String airline;
	private String fromplace;
	private String toplace;

	//@Temporal(TemporalType.DATE)
	private Date startDate;

	//@Temporal(TemporalType.DATE)
	private Date endDate;
	
	private String scheduledDays;
	
	private Integer totalBusinessClassSeats;
	
	private Integer totalNonBusinessClassSeats;
	
	private Double ticketCost;
	
	private Double roundTripCost;
	
	private Integer numberofRows;
	
	private String mealType;
	
	private Boolean flightStatus ;
	
	private String seatNumbers;
}
