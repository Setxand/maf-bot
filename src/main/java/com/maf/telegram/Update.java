package com.maf.telegram;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Update {
	@JsonProperty("update_id")
	private Integer updateId;
	@JsonProperty("callback_query")
	private CallBackQuery callBackQuery;
	private Message message;
}
