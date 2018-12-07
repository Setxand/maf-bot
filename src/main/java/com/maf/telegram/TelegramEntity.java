package com.maf.telegram;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TelegramEntity {
	private Integer offset;
	private Integer length;
	private String type;
}
