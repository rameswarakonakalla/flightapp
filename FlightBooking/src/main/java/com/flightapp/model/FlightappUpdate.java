package com.flightapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightappUpdate {

	private Integer flightNumber;
	private String airline;
	private String fromplace;
	private String toplace;
	private Boolean flightStatus;

}
