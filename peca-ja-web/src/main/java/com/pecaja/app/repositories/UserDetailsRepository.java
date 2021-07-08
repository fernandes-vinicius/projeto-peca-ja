package com.pecaja.app.repositories;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.pecaja.app.services.UserService;

@Repository
@Transactional
public class UserDetailsRepository implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.pecaja.app.models.User user = userService.findByUsername(username);

		if (user == null)
			throw new UsernameNotFoundException("Usuario não encontrado!");

		return new User(user.getUsername(), user.getPassword(), true, true, true, true, user.getAuthorities());
	}

}
