package com.maf.telegram.button;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class KeyboardButton implements Button {
	private String text;

	public KeyboardButton(String text) {
		this.text = text;
	}
}
