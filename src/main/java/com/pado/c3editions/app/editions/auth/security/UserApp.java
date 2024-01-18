package com.pado.c3editions.app.editions.auth.security;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.pado.c3editions.app.editions.auth.users.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserApp implements UserDetails 
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public Users user;
	
	public UserApp(Users users) {
		user=users;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
//		new SimpleGrantedAuthority(email)
		List<GrantedAuthority> authorities=
		user.getPermissions().stream().map(s -> {return new SimpleGrantedAuthority(s);}).collect(Collectors.toList());
		if(Objects.nonNull(user.getRole()))
			authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return !user.isExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return !user.isLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return !user.isDisabled();
	}


	public Users getUser(){
		return user;
	}

}
