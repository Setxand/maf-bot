package com.maf.user.service;

import lombok.Getter;

@Getter
public enum Buttons {

	ORDER("Замовити"),
	CANCEL("Відмінити");

	private String value;
	Buttons(String value) {
		this.value = value;
	}
}
