package com.maf.user.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class User {

	@Id
	private Integer chatId;

	@Enumerated(EnumType.STRING)
	private UserStatus status;
	private String orderCheck;

	public User() {
	}
}
