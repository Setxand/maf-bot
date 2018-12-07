package com.maf.telegram;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Message {
	@JsonProperty("message_id")
	private Integer messageId;
	private UserDTO from;
	private Integer date;
	private Chat chat;
	private String text;
	private Platform platform;
	private List<TelegramEntity> entities;
	private List<Photo> photo;

}
