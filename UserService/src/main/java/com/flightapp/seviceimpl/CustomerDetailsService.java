package com.flightapp.seviceimpl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.flightapp.exception.UnauthorizedException;
import com.flightapp.model.AdminLoginDetails;
import com.flightapp.model.UserData;
import com.flightapp.repository.AdminRepo;
import com.flightapp.repository.UserRepository;

@Service
public class CustomerDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userdao;

	@Autowired
	private AdminRepo repo;

	/**
	 * @param String
	 * @return User
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String uname) {

		try {
			Optional<UserData> user = userdao.findByEmailid(uname);
			if (user.isPresent()) {
				return new User(user.get().getEmailid(), user.get().getPassword(), new ArrayList<>());
			} else {
				throw new UsernameNotFoundException("User id not found");
			}
		} catch (Exception e) {

			throw new UnauthorizedException("Username Not Found Exception");
		}

	}

	public UserDetails loadUserByUsernameadmin(String uname) {

		try {
			Optional<AdminLoginDetails> admin = repo.findById(uname);
			if (admin.isPresent()) {
				return new User(admin.get().getUsername(), admin.get().getPassword(), new ArrayList<>());
			} else {
				throw new UsernameNotFoundException("Admin id not found");
			}
		} catch (Exception e) {

			throw new UnauthorizedException("Admin Not Found Exception");
		}

	}
}
