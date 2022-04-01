package com.flightapp.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.flightapp.exception.UserExistsException;
import com.flightapp.model.UserData;
import com.flightapp.repository.UserRepository;
import com.flightapp.seviceimpl.CustomerDetailsService;
import com.flightapp.seviceimpl.JwtUtil;
import com.flightapp.seviceimpl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;
	@Mock
	private CustomerDetailsService custdetailservice;
	@Mock
	private JwtUtil jwtutil;
	@InjectMocks
	private UserServiceImpl userService;
	
	UserDetails userdetails;
	UserData userData;
	Optional<UserData> user;
	
	@Test
	void loginTest() {
		userdetails=new User("k@gmail.com", "konakalla", new ArrayList<>());
		lenient().when(custdetailservice.loadUserByUsername("k@gmail.com")).thenReturn(userdetails);
		UserData userData=new UserData("k@gmail.com", "konakalla");
		lenient().when(userRepository.findByEmailid("k@gmail.com")).thenReturn(Optional.of(userData));
		UserData login=new UserData("k@gmail.com", "konakalla");
		lenient().when(jwtutil.generateToken(userdetails)).thenReturn("token");
		//int statusCodeValue = userService.userLogin(login).getStatusCodeValue();
		assertEquals(200, userService.userLogin(login).getStatusCodeValue());
	}
	
	
	@Test
	void loginTestFail() {
		userdetails=new User("k@gmail.com", "konakalla", new ArrayList<>());
		lenient().when(custdetailservice.loadUserByUsername("k@gmail.com")).thenReturn(userdetails);
		UserData userData=new UserData("k@gmail.com", "konakalla");
		lenient().when(userRepository.findByEmailid("k@gmail.com")).thenReturn(Optional.of(userData));
		userData=new UserData("k@gmail.com", "konakalla1");
		assertEquals(403, userService.userLogin(userData).getStatusCodeValue());
	}
	

	@Test
	void registerTest() {
		UserData userData=new UserData("k@gmail.com", "konakalla");
		lenient().when(userRepository.findByEmailid("k@gmail.com")).thenReturn(Optional.ofNullable(null));
		lenient().when(userRepository.save(userData)).thenReturn(userData);
		assertEquals(201, userService.userRegister(userData).getStatusCodeValue());
	}
//	@Test
//	void registerTestFail() {
//		UserData userData=new UserData("k@gmail.com", "konakalla");
//		when(userRepository.findByEmailid("k@gmail.com")).thenReturn(Optional.ofNullable(userData));
//		assertThrows(UserExistsException.class,()-> userService.userRegister(userData));
//	}
	
	
	@Test
	void registerTestFail2() {
		UserData userData=new UserData("k@gmail.com", "konakalla");
		lenient().when(userRepository.findByEmailid("k@gmail.com")).thenReturn(Optional.of(userData));
		lenient().when(userRepository.save(userData)).thenThrow(RuntimeException.class);
		assertThrows(UserExistsException.class,()-> userService.userRegister(userData));
	}
	

	@Test
	void validateToken() {
		UserData userData=new UserData("k@gmail.com", "konakalla");
		lenient().when(jwtutil.validateToken("token")).thenReturn(true);
		lenient().when(jwtutil.extractUsername("token")).thenReturn("k@gmail.com");
		lenient().when(userRepository.findByEmailid("k@gmail.com")).thenReturn(Optional.ofNullable(userData));
		assertEquals(200, userService.validate("Bearer token").getStatusCodeValue());
	}
	@Test
	void validateTokenFail() {
		//UserData userData=new UserData("k@gmail.com", "konakalla");
		lenient().when(jwtutil.validateToken("token")).thenReturn(true);
		lenient().when(jwtutil.extractUsername("k@gmail.com")).thenReturn("k@gmail.com");
		lenient().when(userRepository.findByEmailid("k@gmail.com")).thenReturn(Optional.ofNullable(null));
		assertEquals(200, userService.validate("Bearer token").getStatusCodeValue());
	}
	

}
