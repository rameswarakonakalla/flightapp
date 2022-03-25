package com.flightapp.seviceimpl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.flightapp.exception.UnauthorizedException;
import com.flightapp.model.AdminLoginDetails;
import com.flightapp.model.AuthResponse;
import com.flightapp.model.UserData;
import com.flightapp.repository.AdminRepo;


@Service
public class AdminDetailsService implements UserDetailsService {
//	@Autowired
//	private UserRepository userdao;

	@Autowired
	private AdminRepo adminRepo;
	/**
	 * @param String
	 * @return User 
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String uname) {
//		AdminLoginDetails adminLoginDetails = new AdminLoginDetails("admin" , "admin");
//		return new User(adminLoginDetails.getUsername() , adminLoginDetails.getPassword() , new ArrayList<>());
		
		try
		{
			Optional<AdminLoginDetails> adminUser = adminRepo.findById(uname);
			if(adminUser.isPresent()) {
				return new User(adminUser.get().getUsername() , adminUser.get().getPassword() ,new ArrayList<>());
			}
			else {
				throw new UsernameNotFoundException("Admin is not  found");
			}
		}
		catch (Exception e) {
			
			throw new UnauthorizedException("Admin Not Found Exception");
		}	
		
		
	}
	
	

}
