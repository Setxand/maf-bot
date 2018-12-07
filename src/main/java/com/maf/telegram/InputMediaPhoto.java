package com.maf.telegram;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InputMediaPhoto implements InputMedia {
	private String type;
	private String media;
	private String caption;
	@JsonProperty("parse_mode")
	private String parseMode;
}
