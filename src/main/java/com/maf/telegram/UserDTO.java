package com.maf.telegram;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDTO {
	private Integer id;
	@JsonProperty("is_bot")
	private Boolean isBot;
	@JsonProperty("first_name")
	private String firstName;
	@JsonProperty("last_name")
	private String lastName;
	@JsonProperty("username")
	private String userName;
	@JsonProperty("language_code")
	private String languageCode;
}
