package com.maf.user.service;

import org.springframework.stereotype.Service;
import telegram.CallBackQuery;

@Service
public class CallBackService {


	private final CallBackDealerService dealerService;

	public CallBackService(CallBackDealerService dealerService) {
		this.dealerService = dealerService;
	}

	public void parseCallBackQuery(CallBackQuery query) {


		switch (TelegramTextCommands.valueOf(query.getData())) {
			case MENU:
				dealerService.menu(query);
				break;
			case CONTACTS:
				dealerService.contacts(query);
				break;
			case MAKE_ORDER:
				dealerService.makeOrder(query);
				break;
			case MAKE_ORDER_DELIVERY:
				dealerService.makeOrderDelivery(query);
				break;
			default:
				throw new IllegalArgumentException("ISE");
		}


	}
}
