package com.flightapp.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.flightapp.model.AdminLoginDetails;
import com.flightapp.model.UserData;
import com.flightapp.repository.AdminRepo;
import com.flightapp.seviceimpl.AdminServiceImpl;
import com.flightapp.seviceimpl.CustomerDetailsService;
import com.flightapp.seviceimpl.JwtUtil;
import com.flightapp.seviceimpl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

	@Mock
	private AdminRepo adminRepo;
	
	@Mock
	private CustomerDetailsService custdetailservice;
	@Mock
	private JwtUtil jwtutil;
	@InjectMocks
	private UserServiceImpl userService;
	
	@InjectMocks
	private AdminServiceImpl adminService;
	
	UserDetails userdetails;
	UserData userData;
	Optional<UserData> user;
	
	@Test
	void adminloginTest() {
		
		userdetails=new User("admin", "admin", new ArrayList<>());
		
		lenient().when(custdetailservice.loadUserByUsernameadmin("admin")).thenReturn(userdetails);
		AdminLoginDetails adminlogin = new AdminLoginDetails("admin", "admin");
		lenient().when(adminRepo.findById("admin")).thenReturn(Optional.of(adminlogin));
		AdminLoginDetails adminlogin1 = new AdminLoginDetails("admin", "admin");
		lenient().when(jwtutil.generateToken(userdetails)).thenReturn("token");
		//int statusCodeValue = userService.userLogin(login).getStatusCodeValue();
		assertEquals(200, adminService.adminLogin(adminlogin1).getStatusCodeValue());
	}
	
	
	@Test
	void adminTestFail() {
		
		userdetails=new User("k@gmail.com", "konakalla", new ArrayList<>());
		lenient().when(custdetailservice.loadUserByUsernameadmin("admin")).thenReturn(userdetails);
		AdminLoginDetails adminlogin = new AdminLoginDetails("admin", "admin");
		lenient().when(adminRepo.findById("admin")).thenReturn(Optional.ofNullable(null));
		adminlogin = new AdminLoginDetails("admin", "admin1");
		assertEquals(403, adminService.adminLogin(adminlogin).getStatusCodeValue());
	}
	

	
	
	@Test
	void adminValidateTokenTest() {
		AdminLoginDetails adminlogin = new AdminLoginDetails("admin", "admin");
		lenient().when(jwtutil.validateToken("token")).thenReturn(true);
		lenient().when(jwtutil.extractUsername("token")).thenReturn("admin");
		lenient().when(adminRepo.findById("admin")).thenReturn(Optional.ofNullable(adminlogin));
		assertEquals(200, adminService.adminValidate("Bearer token").getStatusCodeValue());
	}
	@Test
	void validateTokenFail() {
		lenient().when(jwtutil.validateToken("token1")).thenReturn(true);
		lenient().when(jwtutil.extractUsername("token")).thenReturn("admin");
		lenient().when(adminRepo.findById("admin")).thenReturn(Optional.ofNullable(null));
		assertEquals(200, userService.validate("Bearer token").getStatusCodeValue());
	}
	

}
