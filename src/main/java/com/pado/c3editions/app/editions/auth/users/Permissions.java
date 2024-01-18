package com.pado.c3editions.app.editions.auth.users;

import java.util.List;


import com.pado.c3editions.app.editions.auth.ImplModel;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity

@Table(name="permissions")
public class Permissions extends ImplModel/*implements GrantedAuthority*/{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;

	@ManyToMany
	@JoinTable(
			name="users_permissions",
			joinColumns = {@JoinColumn(name="user_id")},
			inverseJoinColumns = {@JoinColumn(name="permission_id")}
	)
	private List<Users> users;

//	@Override
//	public String getAuthority() {
//		// TODO Auto-generated method stub
//		return name;
//	}
}
