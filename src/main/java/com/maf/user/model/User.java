package com.maf.user.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "m_user")
public class User {

	public enum Role {
		ADMIN,
		USER
	}

	@Id
	private Integer chatId;

	@Enumerated(EnumType.STRING)
	private UserStatus status;
	private String orderCheck;
	private Role role;

	public User() {
	}
}
