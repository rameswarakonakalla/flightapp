package com.flightapp.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateToken {
	private String uid;
	private String name;
	private boolean isValid;
}