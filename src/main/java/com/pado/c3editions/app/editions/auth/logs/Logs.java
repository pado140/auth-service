package com.pado.c3editions.app.editions.auth.logs;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pado.c3editions.app.editions.auth.ImplModel;
import com.pado.c3editions.app.editions.auth.users.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Table(name="logs")
public class Logs extends ImplModel {
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	private String action;
	private String desciption,claz;
	
	private Class<?> clazz;

	@Column(name="livre_id",nullable = true)
	private Long livre;

	@Column(name="projet_id",nullable = true)
	private Long projet;

	@ManyToOne
	@JoinColumn(name="user_id",nullable = true)
	@JsonBackReference("userlog")
	private Users user;
	
}
