package com.maf.telegram.button;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InlineKeyboardButton implements Button {
	private String text;
	private String url;
	@JsonProperty("callback_data")
	private String callBackData;

	public InlineKeyboardButton(String text, String callBackData) {
		this.text = text;
		this.callBackData = callBackData;
	}

	public InlineKeyboardButton() {
	}
}
