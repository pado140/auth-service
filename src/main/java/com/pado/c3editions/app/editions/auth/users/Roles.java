package com.pado.c3editions.app.editions.auth.users;

import java.util.List;

import com.pado.c3editions.app.editions.auth.ImplModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="roles")
public class Roles extends ImplModel/*implements GrantedAuthority*/{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;

	@OneToMany(cascade= CascadeType.ALL,mappedBy = "role")
	private List<Users> users;


//	@Override
//	public String getAuthority() {
//		// TODO Auto-generated method stub
//		return name;
//	}
}
