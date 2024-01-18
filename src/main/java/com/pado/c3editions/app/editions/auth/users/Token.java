package com.pado.c3editions.app.editions.auth.users;

import com.pado.c3editions.app.editions.auth.ImplModel;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Generated;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Data

@Table(name="token")
public class Token extends ImplModel {
	@UuidGenerator()
	private String Token;
	private LocalDateTime created;
	private LocalDateTime modified;
	private boolean enabled;
}
