package com.pado.c3editions.app.editions.auth.security;

import java.util.Optional;

import com.pado.c3editions.app.editions.auth.repository.UsersRepository;
import com.pado.c3editions.app.editions.auth.users.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class UserAppService implements UserDetailsService
{
	
	@Autowired
	private UsersRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<Users> user=userRepository.findByUsername(username);
		if(!user.isPresent()) {
			throw new UsernameNotFoundException(String.format("Utilisateur %s n'existe pas",username));
		}
		
		UserApp userApp=new UserApp(user.get());
		return userApp;
	}
	
	
//	private Collection<? extends GrantedAuthority> getAuthorities(){
//		
//	}
	
}
