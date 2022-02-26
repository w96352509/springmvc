package com.study.springmvc.case04.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.study.springmvc.case04.entity.Stock;

import yahoofinance.YahooFinance;

@Component
public class StockValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Stock.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Stock stock =(Stock)target;
		//基本驗證
		ValidationUtils.rejectIfEmpty(errors,"symbol","stock.symbol.empty");
		ValidationUtils.rejectIfEmpty(errors, "price","stock.price.empty");
		ValidationUtils.rejectIfEmpty(errors, "amount","stock.amount.empty");
		// 進階驗證 使用：https://financequotes-api.com
		yahoofinance.Stock yStock = null;
		try {
			//取得 symbol 
			yStock = YahooFinance.get(stock.getSymbol());
			// 昨收(previousClose) 
			Double previousClose = yStock.getQuote().getPreviousClose().doubleValue();
			// 要驗證資料(price(買進) / amount(數量))
			Double price = stock.getPrice();
			Integer amount = stock.getAmount();
			// 買進價格必須是昨日收盤價的±10%之間 + 預設提醒
			if (price < previousClose * 0.9 || price > previousClose * 1.1 ) {
				errors.rejectValue("price","stock.price.range" 
					, new Object[] {(previousClose * 0.9),(previousClose * 1.1)}
				    ,"買進價格必須是昨日收盤價的±10%之間");
			//預設提醒
				Double currentPrice = yStock.getQuote().getPrice().doubleValue();
				errors.reject("price_info",String.format("作收:%.2f 最新成交價:%.2f", previousClose, currentPrice));
			}
			// 買進數量必須 >= 1000
			   if (amount < 1000) {
				  errors.rejectValue("amount","stock.amount.notenough");
			}
			// 買進股數必須是1000的倍數(1000股 = 1張)
			   if (amount % 1000 != 0) {
				errors.rejectValue("amount", "stock.amount.range");
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (yStock == null) {
				errors.rejectValue("symbol", "stock.symbol.notfound");
			}
		}
	}
}
