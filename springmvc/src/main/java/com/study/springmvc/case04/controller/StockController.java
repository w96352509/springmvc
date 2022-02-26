package com.study.springmvc.case04.controller;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.springmvc.case04.entity.Stock;
import com.study.springmvc.case04.validator.StockValidator;

@Controller
@RequestMapping("/case04/stock")
public class StockController {
  
	//存放紀錄
	private List<Stock> stocks = new CopyOnWriteArrayList<>();
	
	@Autowired
	private StockValidator stockValidator;
	
	@RequestMapping("/")
	public String index(@ModelAttribute Stock stock , Model model) {
		model.addAttribute("_method", "POST");
		model.addAttribute("stocks" , stocks);
		return "case04/stock";
	}
	
	@PostMapping("/")
	public String add(@Valid Stock stock , BindingResult result , Model model) {
		//自主驗證
		stockValidator.validate(stock, result);
		if (result.hasErrors()) {
			model.addAttribute("stocks" , stocks);
			return "case04/stock";
		}
		  stocks.add(stock);
		return "redirect:./";
	}
	
	@DeleteMapping("/{index}")
	public String delete(@PathVariable("index") int index ) {
		stocks.remove(index);
		return "redirect:./";
	}
}
