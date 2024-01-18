package com.pado.c3editions.app.editions.auth.users;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


import com.pado.c3editions.app.editions.auth.dtos.EmployeDto;
import com.pado.c3editions.app.editions.auth.logs.Logs;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import com.pado.c3editions.app.editions.auth.ImplModel;
import org.hibernate.annotations.GeneratedColumn;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class Users extends ImplModel {
	
	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false,unique = true)
	private String username;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String recover;
	@Column(columnDefinition = "bit(1)")
	private boolean locked=false,disabled=false,expired=false,changepassword=true;
	@ManyToOne
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "role_id")
	private Roles role;

	@Column(nullable = false,name = "employe_id")
	private Long employeId;

//	@GeneratedColumn("'USER-'+`id`")
//	private String customid;

	private String permission;

	@Transient
	private List<String> permissions;

	@OneToMany(orphanRemoval = true)
	@JoinColumn(name = "user_id")
	private Set<Logs> logs = new LinkedHashSet<>();

	public List<String> getPermissions() {
		return List.of(permission.split(","));
	}

	@Override
	public String toString() {
		return "Users{" +
				"id=" + this.getId() +
				", email='" + username + '\'' +
				", recover='" + recover + '\'' +
				", locked=" + locked +
				", disabled=" + disabled +
				", expired=" + expired +
				", role=" + role +
				", permission='" + permission + '\'' +
				", permissions=" + permissions +
				'}';
	}

	@Transient
	private EmployeDto employe;
}
