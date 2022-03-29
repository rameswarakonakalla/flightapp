package com.flightapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import com.flightapp.model.AdminLoginDetails;
import com.flightapp.model.AuthResponse;
import com.flightapp.model.UserData;
import com.flightapp.service.AdminServices;
import com.flightapp.service.UserServices;

@ExtendWith(MockitoExtension.class)
class UserServiceControllerTest {
	@Mock
	UserServices userServices;
	
	
	@Mock
	AdminServices adminServices;
	
	@InjectMocks
	UserServiceController userController;
	
	UserDetails userdetails;
	UserData userData;
	AdminLoginDetails adminLoginDetails;
	//Optional<UserData> user;
	
	
	
	@Test
	void registerTest() {
		
		userData=new UserData("k@gmail.com" , "konakalla");
		when(userServices.userRegister(userData)).thenReturn(new ResponseEntity<Object>("Added Successfully",HttpStatus.CREATED));
		assertEquals(201, userController.userRegister(userData).getStatusCodeValue());
	}
	
	@Test
	void loginTest() {
		userData=new UserData("k@gmail.com" , "konakalla");
		
		when(userServices.userLogin(userData)).thenReturn(new ResponseEntity<>(new AuthResponse(),HttpStatus.OK));
		assertEquals(200, userController.userLogin(userData).getStatusCodeValue());
	}
	
	@Test
	void validateTest() {
		when(userServices.validate("token")).thenReturn(new ResponseEntity<>(new AuthResponse(),HttpStatus.OK));
		assertEquals(200, userController.getValidity("token").getStatusCodeValue());
	}
	
	@Test
	void adminLoginTest() {
		adminLoginDetails = new AdminLoginDetails("admin" , "admin");		
		when(adminServices.adminLogin(adminLoginDetails)).thenReturn(new ResponseEntity<>(new AuthResponse(),HttpStatus.OK));
		assertEquals(200, userController.adminLogin(adminLoginDetails).getStatusCodeValue());
	}
	
	@Test
	void adminValidateTest() {
		
		when(adminServices.adminValidate("token")).thenReturn(new ResponseEntity<>(new AuthResponse(),HttpStatus.OK));
		assertEquals(200, userController.getAdminValidity("token").getStatusCodeValue());
	}
	
}
