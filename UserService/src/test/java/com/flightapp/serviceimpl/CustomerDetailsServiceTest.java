package com.flightapp.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.flightapp.exception.UnauthorizedException;
import com.flightapp.model.AdminLoginDetails;
import com.flightapp.model.UserData;
import com.flightapp.repository.AdminRepo;
import com.flightapp.repository.UserRepository;
import com.flightapp.seviceimpl.CustomerDetailsService;

@ExtendWith(MockitoExtension.class)
class CustomerDetailsServiceTest {

	UserDetails userdetails;

	@Mock
	UserRepository userservice;
	
	@Mock
	AdminRepo adminRepo;

	@InjectMocks
	CustomerDetailsService custdetailservice;

	@Test
	void loadUserByUsernameTest() {

		UserData user1 = new UserData("k@gmail.com" , "konakalla");
		Optional<UserData> data = Optional.of(user1);
		when(userservice.findByEmailid("k@gmail.com")).thenReturn(data);
		UserDetails loadUserByUsername2 = custdetailservice.loadUserByUsername("k@gmail.com");
		assertEquals(user1.getEmailid(), loadUserByUsername2.getUsername());
	}

	@Test
	void loadUserByUsernameTestFail() {
		
		Optional<UserData> data = Optional.ofNullable(null);
		when(userservice.findByEmailid("k@gmail.com")).thenReturn(data);
		assertThrows(UnauthorizedException.class, () -> custdetailservice.loadUserByUsername("k@gmail.com"));
	}

	@Test
	void userNotFound() {

		when(userservice.findByEmailid("k1@gmail.com")).thenReturn(null);
		assertThrows(UnauthorizedException.class, () -> custdetailservice.loadUserByUsername("k@gmail.com"));
	}

	//
	
	
	@Test
	void loadUserByUsernameadminTest() {

		AdminLoginDetails adminLoginDetails = new AdminLoginDetails("admin", "admin");
		Optional<AdminLoginDetails> data = Optional.of(adminLoginDetails);
		when(adminRepo.findById("admin")).thenReturn(data);
		UserDetails loadUserByUsername2 = custdetailservice.loadUserByUsernameadmin("admin");
		assertEquals(adminLoginDetails.getUsername(), loadUserByUsername2.getUsername());
	}

	@Test
	void loadUserByUsernameadminTestFail() {

		Optional<AdminLoginDetails> data = Optional.ofNullable(null);
		when(adminRepo.findById("admins")).thenReturn(data);
		Assertions.assertThrows(UnauthorizedException.class, () -> custdetailservice.loadUserByUsernameadmin("admin"));
	}
	
	
	@Test
	void adminNotFoundTest() {

		when(adminRepo.findById("admin")).thenReturn(null);
		assertThrows(UnauthorizedException.class, () -> custdetailservice.loadUserByUsernameadmin("admin"));
	}

	
}