package com.study.springmvc.case02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.springmvc.case02.service.ArrayDateService;

@Controller
@RequestMapping("/case02/arraydata")
public class ArrayDataController {

	@Autowired
	private ArrayDateService arrayDateService;
	
	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("names" , arrayDateService.getNames());
		model.addAttribute("fruits" , arrayDateService.getFruits());
		return "case02/show_data";
	}
}
