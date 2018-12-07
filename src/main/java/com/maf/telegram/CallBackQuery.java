package com.maf.telegram;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CallBackQuery {
	private Long id;
	private UserDTO from;
	private Message message;
	@JsonProperty("chat_instance")
	private Long chatInstance;
	private String data;
}
