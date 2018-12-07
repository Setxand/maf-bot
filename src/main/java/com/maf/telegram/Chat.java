package com.maf.telegram;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Chat {
	private Integer id;
	private String type;
	private String title;
	@JsonProperty("username")
	private String userName;
	@JsonProperty("first_name")
	private String firstName;
	@JsonProperty("last_name")
	private String lastName;
	private String text;

	public Chat() {
	}

	public Chat(Integer id) {
		this.id = id;
	}
}
